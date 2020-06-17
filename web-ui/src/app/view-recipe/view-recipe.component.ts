import { Component, OnInit } from '@angular/core';
import { identifierModuleUrl, templateJitUrl } from '@angular/compiler';
import { StringMap } from '@angular/compiler/src/compiler_facade_interface';
import { HttpHeaders, HttpClient, HttpEventType } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { KeycloakService } from 'keycloak-angular';
import { stringify } from 'querystring';
// import { receiveMessageOnPort } from 'worker_threads';

class Ingredient {
  id: number;
  name: string;
  unity: string;
  constructor(id, name, unity) {
    this.id = id;
    this.name = name;
    this.unity = unity;
  }
}

class IngredientRecipe {
  id: number;
  name: string;
  unity: string;
  quantity: number;
  constructor(id, name, unity, quantity) {
    this.id = id;
    this.name = name;
    this.unity = unity;
    this.quantity = quantity;
  }
}

class Comment {
  text: string;
  grade: number;
  constructor(text, grade) {
    this.text = text;
    this.grade = grade;
  }
}

class IngredientInFridge {
  detailsID = '';
  quantity = '';
  expiration = '';
  constructor(id, quantity, expiration) {
      this.quantity = quantity;
      this.detailsID = id;
      this.expiration = expiration;
  }
}

class IngredientInFridgeName {
  id = '';
  name = '';
  quantity = '';
  unity = '';
  expiration = '';
  constructor(id, name, quantity, unity, expiration) {
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      this.unity = unity;
      this.expiration = expiration;
  }
}


@Component({
  selector: 'app-view-recipe',
  templateUrl: './view-recipe.component.html',
  styleUrls: ['./view-recipe.component.css']
})

export class ViewRecipeComponent implements OnInit {

  constructor(private http: HttpClient, private route: ActivatedRoute, public keycloak: KeycloakService, private router: Router) { }

  urlBase = 'api/v1/recipe/';

  urlIngredients = 'api/v1/ingredients/minInfos';

  urlFridge = 'api/v1/fridge';

  public id: string;

  public allIngredients: Array<Ingredient> = [];

  public ingredientRecipe: Array<IngredientRecipe> = [];

  public ingredientRecipeOriginal: Array<IngredientRecipe> = [];

  public ingredientsInFridge: Array<IngredientInFridge> = [];

  public ingredientsInFridgeName: Array<IngredientInFridgeName> = [];

  public recipe: any;

  public comments: any;

  public comment: Comment;

  public text: string = null;

  public grade: number = null;

  public json: string;

  public isOutdated: Array<boolean> = [];

  public checkFridge = false;

  public ingredientMissing: Array<number> = [];

  public fridgeEmpty = true;

  public hasAllIngredients = false;

  ngOnInit() {
    this.id = this.route.snapshot.params.id;
    this.getIngredients();
  }

  getIngredients() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlIngredients, headernode).toPromise().then(json => {
      this.addJsonIngredientsToClass(json);
      this.getRecipe(this.urlBase + this.id);
      this.getComments();
    });
  }

  addJsonIngredientsToClass(json) {
    // this.allIngredients = [];
    let ingr;

    for (const ingredient of json) {
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
      this.allIngredients.push(ingr);
    }
  }

  getRecipe(url: string) {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(url, headernode).toPromise().then(json => {
      this.addJsonRecipeIngredientsToClass(json);
      this.getFridge();
      this.recipe = json;
    });
  }

  getFridge() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlFridge, headernode).toPromise().then(json => {
      const temp: any = json;
      if (this.isEmpty(temp.ingredients)) {
      } else {
        this.createClassFromJSON(json);
      }
    });
  }

  isEmpty(obj) {
    return Object.keys(obj).length === 0;
  }

  createClassFromJSON(json) {
    let ingr;
    let ingred;
    let unit;
    for (const ingredient of json.ingredients) {
      const dateNotFormatted = new Date(ingredient.expiration);
      let formattedString = dateNotFormatted.getFullYear() + '-';
      if (dateNotFormatted.getMonth() < 9) {
        formattedString += '0';
      }
      formattedString += (dateNotFormatted.getMonth() + 1);
      formattedString += '-';

      if (dateNotFormatted.getDate() < 10) {
        formattedString += '0';
      }
      formattedString += dateNotFormatted.getDate();
      const today = new Date();
      if (dateNotFormatted < today) {
        this.isOutdated[ingredient.detailsID] = true;
      }
      ingr = new IngredientInFridge(ingredient.detailsID, ingredient.quantity, formattedString);
      const test = this.allIngredients;
      this.ingredientsInFridge.push(ingr);
      for (const ingredientName of this.allIngredients) {
        if (ingredientName.id === ingredient.detailsID) {
          // tslint:disable-next-line: max-line-length
          unit = ingredientName.unity.split('/')[0];
          ingred = new IngredientInFridgeName(ingredient.detailsID, ingredientName.name, ingredient.quantity, unit , formattedString);
          this.ingredientsInFridgeName.push(ingred);
        }
      }
    }
  }

  addJsonRecipeIngredientsToClass(recipe) {
    const ingredients = recipe.ingredients;
    let ingr;
    let ingr2;
    let idIng: number;
    let name;
    let unity;
    let quantity;
    let flag = 0;
    let count = 0;

    for (const ingredient of ingredients) {
      idIng = ingredient.detailsID;
      quantity = ingredient.quantity;

      while (flag < 1) {
        if (this.allIngredients[count].id === idIng) {
          name = this.allIngredients[count].name;
          unity = this.allIngredients[count].unity.split('/')[0];
          flag = 1;
        }
        count = count + 1;
      }
      count = 0;
      flag = 0;

      ingr = new IngredientRecipe(idIng, name, unity, quantity);
      this.ingredientRecipe.push(ingr);
      ingr2 =  new IngredientRecipe(idIng, name, unity, quantity);
      this.ingredientRecipeOriginal.push(ingr2);
    }
  }

  createNewFridgeDone(Fridge, recipe) {
    let temp;
    for (const ingrRecep of recipe) {
      for (const ingrFridge of Fridge) {
        if (ingrRecep.id === parseInt(ingrFridge.detailsID, 10)) {
          temp = parseInt(ingrFridge.quantity, 10) - ingrRecep.quantity;
          ingrFridge.quantity = temp.toString();
        }
      }
    }

    const fridgeTemp = JSON.stringify(Fridge);
    const NewJson = '{"ingredients":'.concat(fridgeTemp).concat('}');
    this.http.put('api/v1/fridge', NewJson, {
      headers: new HttpHeaders(
        {'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        rejectUnauthorized: 'false' }),
      reportProgress: true,
      observe: 'events'
    }).subscribe(events => {
      this.router.navigate(['/my-fridge/']);
    });
  }

  changePeople(people) {
    // let temp;
    for (const ingrRecep of this.ingredientRecipe) {
      for (const ingrOriginal of this.ingredientRecipeOriginal) {
        if (ingrRecep.name === ingrOriginal.name) {
          // temp = parseInt(ingrOriginal.name, 10) * people;
          // ingrRecep.quantity = temp.toString();
          ingrRecep.quantity = ingrOriginal.quantity * (people / this.recipe.people);
        }
      }
    }
  }

  onVerify() {
    let flag = 0;
    this.hasAllIngredients = false;
    let flagIngredients = 0;
    for (const recepIngr of this.ingredientRecipe) {
      this.ingredientMissing[recepIngr.id] = 0;
      for (const myIngr of this.ingredientsInFridgeName) {
        if (myIngr.name === recepIngr.name) {
          if (parseInt(myIngr.quantity, 10) < recepIngr.quantity) {
            this.ingredientMissing[recepIngr.id] = recepIngr.quantity - parseInt(myIngr.quantity, 10);
          }
          flag = 1;
        }
      }
      if (flag === 0) {
        this.ingredientMissing[recepIngr.id] = -1;
      }
      flag = 0;
    }
    // tslint:disable-next-line: prefer-for-of
    for (let i = 0; i < this.ingredientMissing.length; i++) {
      if (this.ingredientMissing[i] === -1 || this.ingredientMissing[i] > 0) {
        flagIngredients = 1;
      }
    }
    if (flagIngredients === 0) {
      this.hasAllIngredients = true;
    }
  }

  onDone() {
    this.createNewFridgeDone(this.ingredientsInFridge, this.ingredientRecipe);
  }

  getComments() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlBase + this.id + '/comments', headernode).toPromise().then(json => {
      this.comments = json;
    });
  }

  changeComment(text) {
    if (text === '') {
      this.text = null;
    }
    this.text = text;
  }

  isInteger(value) {
    let x;
    if (isNaN(value)) {
      return false;
    }
    x = parseFloat(value);
    // tslint:disable-next-line: no-bitwise
    return (x | 0) === x;
  }

  changeGrade(grade) {
    if (this.isInteger(grade) || grade < 0 || grade > 5) {
      this.grade = grade;
    } else {
      this.grade = null;
    }
  }

  onPublish() {
    if (this.comment !== null && this.grade !== null) {
      this.comment = {
        text: this.text,
        grade: this.grade
      };

      this.json = JSON.stringify(this.comment);
      this.http.post(this.urlBase + this.id + '/comment', this.json, {
      headers: new HttpHeaders(
        {'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
         rejectUnauthorized: 'false' }),
      reportProgress: true,
      observe: 'events'
      }).subscribe(events => {
        if (events.type === HttpEventType.Response) {
          this.getComments();
        }
      });
        } else {
      alert('Veuillez entrez un commentaire et une note correcte');
    }
  }
}
