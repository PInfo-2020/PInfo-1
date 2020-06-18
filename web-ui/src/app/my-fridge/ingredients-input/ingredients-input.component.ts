import { FridgeContentComponent } from './../fridge-content/fridge-content.component';
import { NgModule, AfterViewInit, Output, EventEmitter  } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
// tslint:disable-next-line: import-blacklist
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { NON_BINDABLE_ATTR } from '@angular/compiler/src/render3/view/util';
import { KeycloakService } from 'keycloak-angular';

class AddedIngredient {
  name = '';
  quantity = '';
  unity = '';
  expiration = '';
  id = '';
  constructor(name, quantity, id, unity, expiration) {
      this.name = name;
      this.quantity = quantity;
      this.id = id;
      this.unity = unity;
      this.expiration = expiration;
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
  expiration = '';
  constructor(id, name, unity, expiration) {
      this.id = id;
      this.name = name;
      this.unity = unity;
      this.expiration = expiration;
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
  expiration = '';
  constructor(expiration, detailsID, quantity) {
    this.quantity = quantity;
    this.detailsID = detailsID;
    this.expiration = expiration;
  }
}

@Component({
  selector: 'app-ingredients-input',
  templateUrl: './ingredients-input.component.html',
  styleUrls: ['./ingredients-input.component.css']
}
)


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

  public dateToday = '';

  incorrectData: number;

  json: string;

  @ViewChild('list') listValue: any;

  urlMinInfo = 'api/v1/ingredients/minInfos';
  urlFridge = 'api/v1/fridge';


  ngOnInit() {
    this.getIngredients();
  }

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
          const unity = ingred.unity.split('/')[0];
          newIngr = new AddedIngredient(ingred.name, 0, ingred.id, unity, this.dateToday);
          newIngrFridge = new AddedIngredientToFridge(ingred.id, 0, this.dateToday);
          this.addedIngredients.push(newIngr);
          this.addedIngredientsFridge.push(newIngrFridge);
          this.listValue.text = '';
        }
      }
    }
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
    const today = new Date();
    this.dateToday = today.getFullYear() + '-';
    if (today.getMonth() < 9) {
        this.dateToday += '0';
      }
    this.dateToday += (today.getMonth() + 1);
    this.dateToday += '-';
    if (today.getDate() < 10) {
        this.dateToday += '0';
      }
    this.dateToday += today.getDate();

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
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2], this.dateToday);
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
      // tslint:disable-next-line: radix
      if (parseInt(ingredient.id) === parseInt(id)) {
        ingredient.quantity = quantity;
      }
    }
    this.changeIngredients();
    for (const ingredient of this.addedIngredientsFridge) {
      // tslint:disable-next-line: radix
      if (ingredient.detailsID === parseInt(id)) {
        // tslint:disable-next-line: radix
        ingredient.quantity = parseInt(quantity);
      }
    }
    this.changeIngredients();
  }

  changeDate(date: Date, id: string) {
    for (const ingredient of this.addedIngredients) {
      // tslint:disable-next-line: radix
      if (parseInt(ingredient.id) === parseInt(id.split('/')[0])) {
          const dateNotFormatted = new Date(date);
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
          ingredient.expiration = formattedString;
      }
    }
    this.changeIngredients();
    for (const ingredient of this.addedIngredientsFridge) {
      // tslint:disable-next-line: radix
      if (ingredient.detailsID === parseInt(id)) {
        const dateNotFormatted = new Date(date);
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
        ingredient.expiration = formattedString;
      }
    }
    this.changeIngredients();
  }

  changeIngredients() {
    this.ingredientsToBeStringified = [];
    for (const ingred of this.addedIngredients) {
      this.ingredientsToBeStringified.push(new IngredientToBeStringified(ingred.expiration, ingred.quantity, ingred.id));
    }
    this.changedIngredients.emit(this.ingredientsToBeStringified);
  }

  verifyData() {
    this.incorrectData = 0;
  }



  onAdd() {
    console.log(this.addedIngredients);
    this.verifyData();
    if (this.incorrectData === 0) {
      this.ChangeFridge();
      this.addedIngredients.splice(0, this.addedIngredients.length);
    } else {
      this.printErrors();
    }
  }

  reload() {
    location.reload();
  }


  ChangeFridge() {
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
    this.ingredientsInFridge = [];
    for (const ingredient of json.ingredients) {
      console.log(ingredient.expiration);
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
      ingr = new IngredientInFridge(ingredient.detailsID, ingredient.quantity, formattedString);
      this.ingredientsInFridge.push(ingr);
    }
    this.createNewFridge(this.ingredientsInFridge);
   }


   createNewFridge(Fridge) {
    for (const ingredient of Fridge) {
      for (const ingredientAdd of this.addedIngredientsFridge) {
        if (ingredient.detailsID === ingredientAdd.detailsID) {
          ingredient.quantity += ingredientAdd.quantity;
          this.addedIngredientsFridge.splice(this.addedIngredientsFridge.indexOf(ingredientAdd), 1);
        }
      }
    }
    const fridgeTemp = JSON.stringify(Fridge.concat(this.addedIngredientsFridge));
    this.addedIngredientsFridge = [];
    console.log('FrigoTemp : ', fridgeTemp);
    const NewJson = '{"ingredients":'.concat(fridgeTemp).concat('}');
    console.log('Nouveau Frigo : ', NewJson);
    // tslint:disable-next-line: max-line-length
    this.http.put('api/v1/fridge', NewJson, {
      headers: new HttpHeaders(
        {'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
        rejectUnauthorized: 'false' }),
      reportProgress: true,
      observe: 'events'
    }).subscribe(events => {});
  }

  printErrors() {

  }

 }

