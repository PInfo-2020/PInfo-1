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
  quantity = 0;
  detailsID = 0;
  constructor(quantity,id) {
      this.quantity = quantity;
      this.detailsID = id;
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

class IngredientToBeStringified {
  quantity = 0;
  detailsID = 0;
  constructor(quantity, detailsID) {
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

  public ingredients: Array<Ingredient> = [];

  public data: Array<string> = [];

  public addedIngredients: Array<AddedIngredient> = [];

  public addedIngredientsFridge: Array<AddedIngredientToFridge> = [];

  public ingredientsToBeStringified: Array<IngredientToBeStringified> = [];

  @Output() changedIngredients = new EventEmitter<Array<IngredientToBeStringified>>();

  incorrectData: number;

  json: string;

  url = 'api/v1/ingredients/minInfos';

  public SelectedAssetFromSTCombo(ingre) {
    if (!this.listIngredient.includes(ingre)) {
      return;
    }

    console.log(ingre);

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
          console.log('Ingred : ', ingred);
          newIngr = new AddedIngredient(ingred.name, 0, ingred.id, ingred.unity);
          newIngrFridge = new AddedIngredientToFridge(0, ingred.id);
          this.addedIngredients.push(newIngr);
          this.addedIngredientsFridge.push(newIngrFridge);
        }
      }
    }
    console.log('longueur' , this.addedIngredients.length);
    console.log( 'Added Ingredients :' );
    console.log(this.addedIngredients);
    console.log(this.addedIngredientsFridge);
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
    this.http.get(this.url, headernode).toPromise().then(json => {
      console.log(json);
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
    console.log('index : ', index);
    console.log('this.addedIngredients : ', this.addedIngredients[index]);
    this.addedIngredients.splice(index, 1);
  }

  changeQuantity(quantity: string, id: string) {
    for (const ingredient of this.addedIngredients) {
      if (parseInt(ingredient.id) === parseInt(id)) {
        ingredient.quantity = quantity;
      }
    }
    this.changeIngredients();
    console.log('this.addedIngredients : ', this.addedIngredients);
    for (const ingredient of this.addedIngredientsFridge) {
      if (ingredient.detailsID === parseInt(id)) {
        ingredient.quantity = parseInt(quantity);
      }
    }
    this.changeIngredients();
    console.log('this.addedIngredients : ', this.addedIngredientsFridge);
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

  createJSON() {
    console.log(this.addedIngredientsFridge);
    this.json = JSON.stringify(this.addedIngredientsFridge);
    console.log(this.json);
    //tslint:disable-next-line: max-line-length
    this.http.post('api/v1/fridge', this.json, {
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
        //alert('SUCCESS !!');
      }

    })
  }

  printErrors() {

  }

  onAdd() {
    console.log('this.addedIngredients : ', this.addedIngredients);
    this.verifyData();
    if (this.incorrectData === 0) {
      alert('Okeeeeeey');
      this.createJSON();
      //this.addedIngredients.splice(index, 1);
    } else {
      this.printErrors();
    }
  }
 }

