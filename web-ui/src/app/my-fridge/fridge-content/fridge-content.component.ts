import { NgModule, AfterViewInit } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { NON_BINDABLE_ATTR } from '@angular/compiler/src/render3/view/util';
import { KeycloakService } from 'keycloak-angular'
class AddedIngredient {
  id = '';
  quantity = 0;
  expiration = '';
  constructor(id, quantity, expiration) {
      this.id = id;
      this.quantity = quantity;
      this.expiration = expiration;
  }
}

class Ingredient {
  id = '';
  name = '';
  //unity = '';
  constructor(id, name) {
      this.id = id;
      this.name = name;
      //this.unity = unity;
  }
}

class IngredientInFridgeName {
  id = '';
  name = '';
  quantity = '';
  //unity = '';
  expiration = '';
  constructor(id, name, quantity,expiration) {
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      //this.unity = unity;
      this.expiration = expiration;
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

@Component({
  selector: 'app-fridge-content',
  templateUrl: './fridge-content.component.html',
  styleUrls: ['./fridge-content.component.css']
})
export class FridgeContentComponent implements OnInit , AfterViewInit{

  @ViewChild('list') list;

  constructor(private http: HttpClient,  public keycloak: KeycloakService) {

  }

  public listIngredient: Array<Ingredient> = [];

  public ingredients: Array<Ingredient> = [];

  public data: Array<string> = [];

  public addedIngredients: Array<AddedIngredient> = [];

  public ingredientsInFridge: Array<IngredientInFridge> = [];

  public ingredientsInFridgeName: Array<IngredientInFridgeName> = [];

  // private json: Array<Array<string>>;

  urlMinInfo = 'api/v1/ingredients/idName';
  urlFridge = 'api/v1/fridge';

  ngOnInit() {
    this.getIngredients();
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
    let ingred;
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
      const test = this.listIngredient;
      this.ingredientsInFridge.push(ingr);
      console.log('Coucou', this.listIngredient);
      for (const ingredientName of this.listIngredient) {
        console.log('Salut')
        if (ingredientName.id === ingredient.detailsID) {
          // tslint:disable-next-line: max-line-length
          ingred = new IngredientInFridgeName(ingredient.detailsID, ingredientName.name, ingredient.quantity, formatted_string);
          this.ingredientsInFridgeName.push(ingred);
        }
      }
      console.log('raté')
    }
    //this.createNewFridge(this.ingredientsInFridge);
   }

  ngAfterViewInit() {
    /*const contains = value => s => s.toLowerCase().indexOf(value.toLowerCase()) !== -1;

    this.list.asObservable().switchMap(value => Observable.from([this.listIngredient])
      .do(() => this.list.loading = true)
      .map((data) =>  data.filter(contains(value))))
      .subscribe(x => {
          this.data = x;
          this.list.loading = false;
      });*/
  }

  onRemove(index) {
    console.log('avant : ',  this.ingredientsInFridge);
    this.ingredientsInFridge.splice(index, 1);
    console.log('apres : ',  this.ingredientsInFridge);
    this.createNewFridge(this.ingredientsInFridge);
  }

  createNewFridge(Fridge) {
    const fridgeTemp = JSON.stringify(Fridge);
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
      ingr = new Ingredient(ingredient[0], ingredient[1]);
      this.ingredients.push(ingr);
    }
    for (const ingredient of this.ingredients) {
      this.listIngredient.push(ingredient);
    }
  }


  changeQuantity(quantity: string, id: string) {
    for (const ingredient of this.ingredientsInFridge) {
      if (parseInt(ingredient.detailsID) === parseInt(id)) {
        ingredient.quantity = quantity;
      }
      if (parseInt(ingredient.quantity) <= 0)
      {
        const index = this.ingredientsInFridge.indexOf(ingredient);
        this.ingredientsInFridge.splice(index, 1);
      }
    }
    this.createNewFridge(this.ingredientsInFridge);
    }
  }


