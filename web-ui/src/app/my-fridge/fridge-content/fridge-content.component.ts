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
  quantity = '';
  expiration = '';
  constructor(id, quantity, expiration) {
      this.id = id;
      this.quantity = quantity;
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

  constructor(private http: HttpClient,  public keycloak: KeycloakService) { }

  public listIngredient: Array<string> = [];

  public ingredients: Array<Ingredient> = [];

  public data: Array<string> = [];

  public addedIngredients: Array<AddedIngredient> = [];

  // private json: Array<Array<string>>;

  url = 'api/v1/fridge';

  ngOnInit() {
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
    this.http.get(this.url, headernode).toPromise().then(json => {
      console.log(json);
      this.addJsonToClass(json['ingredients']);
    });
  }

  addJsonToClass(json) {
    let ingr;

    for (const ingredient of json) {
      console.log(ingredient)
      ingr = new Ingredient(ingredient['detailsID'], ingredient['quantity'], ingredient['expiration']);
      console.log(ingr)
      this.ingredients.push(ingr);
    }
    for (const ingredient of this.ingredients) {
      this.listIngredient.push(ingredient.id, ingredient.quantity, ingredient.expiration);
      console.log(this.listIngredient)
    }
  }

  ngAfterViewInit() {
    const contains = value => s => s.toLowerCase().indexOf(value.toLowerCase()) !== -1;

    this.list.asObservable().switchMap(value => Observable.from([this.listIngredient])
      .do(() => this.list.loading = true)
      .map((data) =>  data.filter(contains(value))))
      .subscribe(x => {
          this.data = x;
          this.list.loading = false;
      });
  }
 }


