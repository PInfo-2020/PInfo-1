import { NgModule, AfterViewInit, Output, EventEmitter  } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
// tslint:disable-next-line: import-blacklist
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { NON_BINDABLE_ATTR } from '@angular/compiler/src/render3/view/util';
import { KeycloakService } from 'keycloak-angular';

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
  unity = '';
  constructor(id, name, unity) {
      this.id = id;
      this.name = name;
      this.unity = unity;
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
  selector: 'app-my-fridge',
  templateUrl: './my-fridge.component.html',
  styleUrls: ['./my-fridge.component.css']
})
export class MyFridgeComponent implements OnInit , AfterViewInit {

    constructor(private http: HttpClient,  public keycloak: KeycloakService) {

    }

    @ViewChild('list') list;

    public clickedEvent: Event;

    public listIngredient: Array<Ingredient> = [];

    public ingredients: Array<Ingredient> = [];

    public data: Array<string> = [];

    public addedIngredients: Array<AddedIngredient> = [];

    public ingredientsInFridge: Array<IngredientInFridge> = [];

    public ingredientsInFridgeName: Array<IngredientInFridgeName> = [];

    public isOutdated: Array<boolean> = [];

    // private json: Array<Array<string>>;

    urlMinInfo = 'api/v1/ingredients/minInfos';
    urlFridge = 'api/v1/fridge';

    ngOnInit() {
      this.getIngredients();
    }

    getIngredients() {
      this.listIngredient.length = 0;
      this.ingredients = [];
      this.data = [];
      this.addedIngredients = [];
      this.ingredientsInFridge = [];
      this.ingredientsInFridgeName = [];
      this.isOutdated = [];

      const headernode = {
        headers: new HttpHeaders(
            { Accept: 'application/json' ,
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
             rejectUnauthorized: 'false' })
        };
      this.http.get(this.urlMinInfo, headernode).toPromise().then(json => {
        this.addJsonToClass(json);
      }).then(json => {this.getFridge(); });
    }

    addJsonToClass(json) {
      let ingr;
      this.listIngredient.length = 0 ;
      for (const ingredient of json) {
        ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
        this.listIngredient.push(ingr);
      }
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
      let unit;
      this.ingredientsInFridgeName.splice(0, this.ingredientsInFridgeName.length);
      for (const ingredient of json.ingredients) {
        const dateNotFormatted = new Date(ingredient.expiration);
        const today = new Date();
        let compareExpiration = dateNotFormatted.getFullYear();
        let compareToday = today.getFullYear();
        let formattedString = dateNotFormatted.getFullYear() + '-';
        if (dateNotFormatted.getMonth() < 9) {
          formattedString += '0';
          compareExpiration*10;
          compareToday*10;
        }
        formattedString += (dateNotFormatted.getMonth() + 1);
        compareExpiration += (dateNotFormatted.getMonth() + 1);
        compareToday += (today.getMonth() + 1);
        formattedString += '-';

        if (dateNotFormatted.getDate() < 10) {
          formattedString += '0';
          compareExpiration*10;
          compareToday*10;
        }
        formattedString += dateNotFormatted.getDate();
        compareExpiration += dateNotFormatted.getDate();
        compareToday += today.getDate();
        if (compareExpiration < compareToday) {
          this.isOutdated[ingredient.detailsID] = true;
        }
        ingr = new IngredientInFridge(ingredient.detailsID, ingredient.quantity, formattedString);
        const test = this.listIngredient;
        this.ingredientsInFridge.push(ingr);
        for (const ingredientName of this.listIngredient) {
          if (ingredientName.id === ingredient.detailsID) {
            // tslint:disable-next-line: max-line-length
            unit = ingredientName.unity.split('/')[0];
            ingred = new IngredientInFridgeName(ingredient.detailsID, ingredientName.name, ingredient.quantity, unit , formattedString);
            this.ingredientsInFridgeName.push(ingred);
          }
        }
      }
    }

    ngAfterViewInit() {
    }

    onRemove(index) {
      this.ingredientsInFridge.splice(index, 1);
      this.createNewFridge(this.ingredientsInFridge);
      this.getIngredients();
    }

    createNewFridge(Fridge) {
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
      }).toPromise().then(json => {
        this.getIngredients();
      });
    }


    changeQuantity(quantity: string, id: string) {
      for (const ingredient of this.ingredientsInFridge) {
        // tslint:disable-next-line: radix
        if (parseInt(ingredient.detailsID) === parseInt(id)) {
          ingredient.quantity = quantity;
        }
        // tslint:disable-next-line: radix
        if (parseInt(ingredient.quantity) <= 0) {
          const index = this.ingredientsInFridge.indexOf(ingredient);
          this.ingredientsInFridge.splice(index, 1);
        }
      }
      this.createNewFridge(this.ingredientsInFridge);
      }
    }



