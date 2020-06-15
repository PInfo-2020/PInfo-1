import { NgModule, AfterViewInit, Output, EventEmitter  } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { NON_BINDABLE_ATTR } from '@angular/compiler/src/render3/view/util';
import { KeycloakService } from 'keycloak-angular'

class AddedIngredient {
  name = '';
  quantity = '';
  unity = '';
  id = '';
  constructor(name, quantity, id, unity) {
      this.name = name;
      this.quantity = quantity;
      this.id = id;
      this.unity = unity;
  }
}

class AddedIngredientToFridge {
  detailsID = 0;
  quantity = 0;
  expiration = '';
  constructor(id, quantity, expiration) {
      this.quantity = quantity;
      this.detailsID = id;
      this.expiration = expiration;
  }
}

class Ingredient {
  id = '';
  name = '';
  unity = '';
  constructor(id, name, unity) {
      this.id = id;
      this.name = name;
      this.unity = unity;
  }
}

class IngredientInFridge {
  detailsID = 0;
  quantity = 0;
  expiration = '';
  constructor(id, quantity, expiration) {
      this.quantity = quantity;
      this.detailsID = id;
      this.expiration = expiration;
  }
}

class IngredientToBeStringified {
  detailsID = 0;
  quantity = 0;
  constructor(detailsID, quantity) {
    this.quantity = quantity;
    this.detailsID = detailsID;
  }
}

@Component({
  selector: 'app-ingredients-input',
  templateUrl: './ingredients-input.component.html',
  styleUrls: ['./ingredients-input.component.css']
})


export class IngredientsInputComponent implements OnInit, AfterViewInit {

  @ViewChild('list') list;

  constructor(private http: HttpClient,  public keycloak: KeycloakService) { }

  public listIngredient: Array<string> = [];

  public listIngredientFridge: Array<string> = [];

  public ingredients: Array<Ingredient> = [];

  public ingredientsInFridge: Array<IngredientInFridge> = [];

  public data: Array<string> = [];

  public addedIngredients: Array<AddedIngredient> = [];

  public addedIngredientsFridge: Array<AddedIngredientToFridge> = [];

  public ingredientsToBeStringified: Array<IngredientToBeStringified> = [];

  @Output() changedIngredients = new EventEmitter<Array<IngredientToBeStringified>>();

  incorrectData: number;

  json: string;

  urlMinInfo = 'api/v1/ingredients/minInfos';
  urlFridge = 'api/v1/fridge';

  public SelectedAssetFromSTCombo(ingre) {
    if (!this.listIngredient.includes(ingre)) {
      return;
    }

    let alreadyIn = 0;

    for (const ingredient of this.addedIngredients) {
      if (ingredient.name === ingre) {
        alreadyIn = 1;
      }
    }
    let newIngr;
    let newIngrFridge;

    if (alreadyIn === 0)  {
      for (const ingred of this.ingredients) {
        if (ingred.name === ingre) {
          newIngr = new AddedIngredient(ingred.name, 0, ingred.id, ingred.unity);
          newIngrFridge = new AddedIngredientToFridge(ingred.id, 0, '2020-02-02');
          this.addedIngredients.push(newIngr);
          this.addedIngredientsFridge.push(newIngrFridge);
        }
      }
    }
  }


  ngOnInit() {
    this.getIngredients();
  }


  ngAfterViewInit() {
    const contains = value => s => s.toLowerCase().indexOf(value.toLowerCase()) !== -1;

    this.list.filterChange.asObservable().switchMap(value => Observable.from([this.listIngredient])
      .do(() => this.list.loading = true)
      .map((data) =>  data.filter(contains(value))))
      .subscribe(x => {
          this.data = x;
          this.list.loading = false;
      });
  }

  getIngredients() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlMinInfo, headernode).toPromise().then(json => {
      this.addJsonToClass(json);
    });
  }

  addJsonToClass(json) {
    let ingr;

    for (const ingredient of json) {
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
      this.ingredients.push(ingr);
    }
    for (const ingredient of this.ingredients) {
      this.listIngredient.push(ingredient.name);
    }
  }


  onRemove(index) {
    this.addedIngredients.splice(index, 1);
  }

  changeQuantity(quantity: string, id: string) {
    for (const ingredient of this.addedIngredients) {
      if (parseInt(ingredient.id) === parseInt(id)) {
        ingredient.quantity = quantity;
      }
    }
    this.changeIngredients();
    for (const ingredient of this.addedIngredientsFridge) {
      if (ingredient.detailsID === parseInt(id)) {
        ingredient.quantity = parseInt(quantity);
      }
    }
    this.changeIngredients();
  }

  changeIngredients() {
    this.ingredientsToBeStringified = [];
    for (const ingred of this.addedIngredients) {
      this.ingredientsToBeStringified.push(new IngredientToBeStringified(ingred.quantity, ingred.id));
    }
    this.changedIngredients.emit(this.ingredientsToBeStringified);
  }

  verifyData() {
    this.incorrectData = 0;
  }



  onAdd() {
    console.log('this.addedIngredients : ', this.addedIngredients);
    this.verifyData();
    if (this.incorrectData === 0) {
      this.ChangeFridge();
      this.addedIngredients.splice(0, this.addedIngredients.length);
    } else {
      this.printErrors();
    }
  }


  ChangeFridge(){
    this.getFridge();
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
      this.createClassFromJSON(json);
    });
  }

  createClassFromJSON(json) {
    let ingr;
    for (const ingredient of json['ingredients']) {
      var date_not_formatted = new Date(ingredient.expiration);
      var formatted_string = date_not_formatted.getFullYear() + '-';
      if (date_not_formatted.getMonth() < 9) {
        formatted_string += '0';
      }
      formatted_string += (date_not_formatted.getMonth() + 1);
      formatted_string += '-';

      if(date_not_formatted.getDate() < 10) {
        formatted_string += '0';
      }
      formatted_string += date_not_formatted.getDate();
      ingr = new IngredientInFridge(ingredient.detailsID, ingredient.quantity, formatted_string);
      this.ingredientsInFridge.push(ingr);
    }
    this.createNewFridge(this.ingredientsInFridge);
   }


   createNewFridge(Fridge) {
    console.log("ca passe le dernier");
    console.log('Ingredient a ajouter : ', this.addedIngredientsFridge);
    console.log('Frigo initial : ', Fridge);
    for (const ingredient of Fridge) {
      for (const ingredientAdd of this.addedIngredientsFridge) {
        if (ingredient.detailsID === ingredientAdd.detailsID) {
          ingredient.quantity += ingredientAdd.quantity;
          this.addedIngredientsFridge.splice(this.addedIngredientsFridge.indexOf(ingredientAdd), 1);
        }
      }
    }
    const fridgeTemp = JSON.stringify(Fridge.concat(this.addedIngredientsFridge));
    console.log('FrigoTemp : ', fridgeTemp);
    const NewJson = '{"ingredients":'.concat(fridgeTemp).concat('}');
    console.log('Nouveau Frigo : ', NewJson);
    //tslint:disable-next-line: max-line-length
    this.http.put('api/v1/fridge', NewJson, {
      headers: new HttpHeaders(
        {'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        rejectUnauthorized: 'false' }),
      reportProgress: true,
      observe: 'events'
    }).subscribe(events => {
      if (events.type === HttpEventType.Response) {
        console.log(events.body);
        alert('SUCCESS !!');
      }

    });
  }

  printErrors() {

  }


 }

