import { NgModule, AfterViewInit, Input, SimpleChange } from '@angular/core';
import { Component, OnInit, ViewChild, OnChanges } from '@angular/core';
// import { Observable } from 'rxjs/Rx';
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
  selector: 'app-fridge-content',
  templateUrl: './fridge-content.component.html',
  styleUrls: ['./fridge-content.component.css']
})
export class FridgeContentComponent implements OnInit , AfterViewInit {

  @ViewChild('list') list;

  constructor(private http: HttpClient,  public keycloak: KeycloakService) {

  }

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

    for (const ingredient of json) {
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
      this.ingredients.push(ingr);
    }
    for (const ingredient of this.ingredients) {
      this.listIngredient.push(ingredient);
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
    for (const ingredient of json.ingredients.sort()) {
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
      let today = new Date();
      if (dateNotFormatted < today) {
        this.isOutdated[ingredient.detailsID] = true;
      }
      ingr = new IngredientInFridge(ingredient.detailsID, ingredient.quantity, formattedString);
      const test = this.listIngredient;
      this.ingredientsInFridge.push(ingr);
      console.log('this.listIngredient', this.listIngredient);
      for (const ingredientName of this.listIngredient) {
        console.log('Dans for');
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
  }

  createNewFridge(Fridge) {
    const fridgeTemp = JSON.stringify(Fridge);
    console.log('FrigoTemp : ', fridgeTemp);
    const NewJson = '{"ingredients":'.concat(fridgeTemp).concat('}');
    console.log('Nouveau Frigo : ', NewJson);
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


