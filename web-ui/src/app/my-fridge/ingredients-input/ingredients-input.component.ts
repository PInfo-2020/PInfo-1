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

  incorrectData: number;

  json: string;

  urlMinInfo = 'api/v1/ingredients/minInfos';
  urlFridge = 'api/v1/fridge';



  public SelectedAssetFromSTCombo(ingre) {

  }


  ngOnInit() {

  }


  ngAfterViewInit() {

  }

  getIngredients() {

  }

  addJsonToClass(json) {

  }


  onRemove(index) {
    this.addedIngredients.splice(index, 1);
  }

  changeQuantity(quantity: string, id: string) {

  }

  changeDate(date: Date, id: string) {

  }

  changeIngredients() {

  }

  verifyData() {
    this.incorrectData = 0;
  }



  onAdd() {

  }


  ChangeFridge() {
    this.getFridge();
  }

  getFridge() {

  }

  createClassFromJSON(json) {

   }


   createNewFridge(Fridge) {

  }

  printErrors() {

  }

 }

