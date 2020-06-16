import { Component, OnInit } from '@angular/core';
import { identifierModuleUrl } from '@angular/compiler';
import { StringMap } from '@angular/compiler/src/compiler_facade_interface';
import { HttpHeaders, HttpClient, HttpEventType } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

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

class Comment{
  text: string;
  grade: number;
  constructor(text, grade) {
    this.text = text;
    this.grade = grade;
  }
}

@Component({
  selector: 'app-view-recipe',
  templateUrl: './view-recipe.component.html',
  styleUrls: ['./view-recipe.component.css']
})

export class ViewRecipeComponent implements OnInit {

  constructor(private http: HttpClient, private route: ActivatedRoute) { }

  urlBase = 'api/v1/recipe/';

  urlIngredients = 'api/v1/ingredients/minInfos';

  public id: string;

  public allIngredients: Array<Ingredient> = [];

  public ingredientRecipe: Array<IngredientRecipe> = [];

  public recipe: any;

  public comments: any;

  public comment: Comment;

  public text: string = null;

  public grade: number = null;

  public json: string;

  ngOnInit() {
    this.id = this.route.snapshot.params.id;
    console.log(this.id);
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
      this.recipe = json;
      console.log('recipe', this.recipe);
      // console.log(this.ingredientRecipe);
    });
  }

  addJsonRecipeIngredientsToClass(recipe) {
    const ingredients = recipe.ingredients;
    let ingr;
    let idIng: number;
    let name;
    let unity;
    let quantity;
    let flag = 0;
    let count = 0;

    for (const ingredient of ingredients) {
      console.log(ingredient);
      idIng = ingredient.detailsID;
      quantity = ingredient.quantity;
      // console.log('all ingredients', this.allIngredients);

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
    }
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
      console.log(this.comments);
    });
  }

  changeComment(text) {
    if (text === '') {
      console.log('comment shit');
      this.text = null;
    }
    this.text = text;
    console.log('comment ok');
  }

  isInteger(value) {
    let x;
    if (isNaN(value)) {
      return false;
    }
    x = parseFloat(value);
    return (x | 0) === x;
  }

  changeGrade(grade) {
    if (this.isInteger(grade) || grade < 0 || grade > 5) {
      this.grade = grade;
      console.log('grade ok');
    } else {
      this.grade = null;
      console.log('grade shit');
    }
  }

  onPublish() {
    if (this.comment !== null && this.grade !== null) {
      this.comment = {
        text: this.text,
        grade: this.grade
      };
      console.log(this.comment);

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
          // Rediriger vers la page de la recette !
        }
      });
    // this.ngOnInit();

    } else {
      alert('Veuillez mettre un comentaire et une note correcte');
    }
  }




}
