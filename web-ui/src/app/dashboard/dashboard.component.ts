import { DifficultyInputComponent } from './../add-receipe/difficulty-input/difficulty-input.component';
import { numberSymbols } from '@progress/kendo-angular-intl';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { stringify } from 'querystring';

class Recipe {
  id: number;
  name: string;
  time: number;
  difficulty: number;
  ingredients: Array<Ingredient>;
  constructor(id, name, time, difficulty, ingredients) {
    this.id = id;
    this.name = name;
    this.time = time;
    this.difficulty = difficulty;
    this.ingredients = ingredients;
  }
}

class Ingredient {
  id: number;
  name: string;
  constructor(id, name) {
    this.id = id;
    this.name = name;
  }
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {

  constructor(private http: HttpClient, public keycloak: KeycloakService) { }

  public recipes: Array<Recipe> = [];

  public filteredRecipes: Array<Recipe> = [];

  public ingredients: any = [];

  public myIngredients: Array<Ingredient> = [];

  public myIngredientsArray: Array<string> = [];

  public keyword = '';

  urlRecipes = 'api/v1/recipe/recipes';

  urlIngr = 'api/v1/ingredients/idName';

  urlSearchPrefix = 'api/v1/recipe/search/';

  urlSearch = '';

  ngOnInit() {
    this.getRecipes();
    this.filteredRecipes = this.recipes;
    // this.onSearch('test');
  }

  getIngredients() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlIngr, headernode).toPromise().then(json => {
      console.log(json);
      // ingredients = json;
    });
  }

  getRecipes() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlRecipes, headernode).toPromise().then(json => {
      console.log(json);
      // parsing function
    });
  }

  sendRecipes(e) {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.post(this.urlSearch, headernode).toPromise().then(json => {
      console.log(json);
      // parsing function
    });
  }

  onClick(i) {
    console.log(i);
  }

  changeKeyword(kw: string) {
    this.keyword = kw;
  }

  onSearch() {
    this.urlSearch = this.urlSearchPrefix + this.keyword;
    console.log(this.urlSearch);

  }

  changeKeywordAndSearch(kw: string) {
    this.keyword = kw;
    this.onSearch();
  }

  myFridgeFilter() {
    let ingrFlag = 0;
    let bool;

    for (const recip of this.recipes) {
      ingrFlag = 0;
      for (const ingr of recip.ingredients) {
         bool = this.myIngredientsArray.includes('ingr');
      }
    }
  }

  myIngredientsToArray() {
    for (const ingr of this.myIngredients) {
      this.myIngredientsArray.push(ingr.name);
    }
  }

}
