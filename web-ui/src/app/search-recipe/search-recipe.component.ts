import { DifficultyInputComponent } from '../add-receipe/difficulty-input/difficulty-input.component';
import { numberSymbols } from '@progress/kendo-angular-intl';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { stringify } from 'querystring';
import { Router } from '@angular/router';

class SimpleRecipe {
  name: string;
  time: number;
  difficulty: number;
  picture: string;
  grade: number;
  id: number;

  constructor(name, time, difficulty, picture, grade, id) {
    this.name = name;
    this.time = time;
    this.difficulty = difficulty;
    this.picture = picture;
    this.grade = grade;
    this.id = id;
  }
}

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
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})

export class SearchRecipeComponent implements OnInit {

  constructor(private http: HttpClient, public keycloak: KeycloakService, private router: Router) { }

  public filteredRecipes: Array<Recipe> = [];

  public myIngredients: Array<Ingredient> = [];

  public myIngredientsArray: Array<string> = [];

  public recipeList: Array<SimpleRecipe> = [];

  public keyword: string;

  public keywordIngr = '';

  public fridgeIngredientsOnly = false;

  urlFridge = 'api/v1/fridge';

  urlSearchPrefix = 'api/v1/recipe/search/';

  urlSearch = '';

  ngOnInit() {
  }

  getIngredients() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlFridge, headernode).toPromise().then(json => {
      this.addJsonToClass(json);
    }).then(json => {this.search(); });
  }

  addJsonToClass(json) {
    this.keywordIngr = '?';
    for (const ingredient of json.ingredients) {
      this.keywordIngr = this.keywordIngr + 'id=' + ingredient.detailsID + '&quantity=' + ingredient.quantity + '&';
    }
  }

  onClick(i) {
    console.log(i);
  }

  changeKeyword(kw: string) {
    this.keyword = kw;
  }

  onSearch() {
    if (this.keyword) {
    this.urlSearch = this.urlSearchPrefix + this.keyword ;
    if (this.fridgeIngredientsOnly) {
        this.getIngredients();
    } else {
      this.search();
    }
  } else {console.log('ERREUR')}
  }

  search() {
    this.recipeList = [];
    this.urlSearch = this.urlSearch + this.keywordIngr ;
    console.log(this.urlSearch);
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlSearch, headernode).toPromise().then(json => {
      this.recipeFromJsonToClass(json);
    });
  }

  recipeFromJsonToClass(json) {
    let recipe;

    for (const userRecipe of json) {
      // tslint:disable-next-line: max-line-length
      recipe = new SimpleRecipe(userRecipe.name, userRecipe.preparationTime, userRecipe.difficulty, userRecipe.picture, userRecipe.grade, userRecipe.id);
      this.recipeList.push(recipe);
    }
  }

  changeKeywordAndSearch(kw: string) {
    this.keyword = kw;
    this.onSearch();
  }

  /*myFridgeFilter() {
    let ingrFlag = 0;
    let bool;

    for (const recip of this.recipes) {
      ingrFlag = 0;
      for (const ingr of recip.ingredients) {
         bool = this.myIngredientsArray.includes('ingr');
      }
    }
  }*/

  myIngredientsToArray() {
    for (const ingr of this.myIngredients) {
      this.myIngredientsArray.push(ingr.name);
    }
  }

  onToggle(event) {
    this.fridgeIngredientsOnly = event.checked;
  }

  isInteger(value) {
    let x;
    if (isNaN(value)) {
      return false;
    }
    x = parseFloat(value);
    return (x | 0) === x;
  }

  redirectToRecipe(recipeId) {
    if (this.isInteger(recipeId)) {
      this.router.navigate(['/view-recipe/' + recipeId]);
    }
  }

}
