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

  public ingredientsInFridge: Array<IngredientInFridge> = [];

  // private json: Array<Array<string>>;

  urlFridge = 'api/v1/fridge';

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
 }


