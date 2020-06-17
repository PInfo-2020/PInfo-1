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
  quantity = '';
  detailsID = '';
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

  public ingredientsToBeStringified: Array<IngredientToBeStringified> = [];

  @Output() changedIngredients = new EventEmitter<Array<IngredientToBeStringified>>();

  url = 'api/v1/ingredients/minInfos';

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

    if (alreadyIn === 0)  {
      for (const ingred of this.ingredients) {
        if (ingred.name === ingre) {
          newIngr = new AddedIngredient(ingred.name, 0, ingred.id, ingred.unity);
          this.addedIngredients.push(newIngr);
        }
      }
    }
  }

  ngOnInit() {
    this.getIngredients();
    // console.log(this.ingredients);
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

  changeQuantity(quantity: number, id: string) {
    for (const ingredient of this.addedIngredients) {
      if (parseInt(ingredient.id) === parseInt(id)) {
        ingredient.quantity = quantity.toString();
      }
    }
    this.changeIngredients();
  }

  changeIngredients() {
    this.ingredientsToBeStringified = [];
    for (const ingred of this.addedIngredients) {
      this.ingredientsToBeStringified.push(new IngredientToBeStringified(ingred.quantity.trim(), ingred.id));
    }
    this.changedIngredients.emit(this.ingredientsToBeStringified);
  }
 }


