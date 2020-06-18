import { DifficultyInputComponent } from '../add-receipe/difficulty-input/difficulty-input.component';
import { numberSymbols } from '@progress/kendo-angular-intl';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';
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

  userDetails: KeycloakProfile;

  constructor(private http: HttpClient, public keycloak: KeycloakService, private router: Router) { }

  public filteredRecipes: Array<Recipe> = [];

  public myIngredients: Array<Ingredient> = [];

  public myIngredientsArray: Array<string> = [];

  public recipeList: Array<SimpleRecipe> = [];

  public keyword: string;

  public keywordIngr = '';

  public fridgeIngredientsOnly = false;

  public fridgeIsEmpty: boolean;

  public filteredRecipesIsEmpty: boolean;

  urlFridge = 'api/v1/fridge';

  urlSearchPrefix = 'api/v1/recipe/search/';

  urlSearch = '';

  async ngOnInit() {
    if (await this.keycloak.isLoggedIn()) {
      this.userDetails = await this.keycloak.loadUserProfile();
    }
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
      const temp: any = json;
      if (this.isEmpty(temp.ingredients)) {
        this.fridgeIsEmpty = true;
      } else {
        this.fridgeIsEmpty = false;
        this.addJsonToClass(json);
      }
    }).then(json => {this.search(); });
  }

  isEmpty(obj) {
    return Object.keys(obj).length === 0;
  }

  addJsonToClass(json) {
    this.keywordIngr = '?';
    for (const ingredient of json.ingredients) {
      this.keywordIngr = this.keywordIngr + 'id=' + ingredient.detailsID + '&quantity=' + ingredient.quantity + '&';
    }
  }

  onClick(i) {
  }

  changeKeyword(kw: string) {
    this.keyword = kw;
  }

  onSearch() {
    this.keywordIngr = '';
    if (this.keyword) {
    this.urlSearch = this.urlSearchPrefix + this.keyword ;
    if (this.fridgeIngredientsOnly) {
        this.getIngredients();
    } else {
      this.search();
    }
  } else {
  }
  }

  search() {
    this.recipeList = [];
    this.urlSearch = this.urlSearch + this.keywordIngr ;
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlSearch, headernode).toPromise().then(json => {
      // filteredRecipesIsEmpty
      const temp: any = json;
      if (this.isEmpty(temp)) {
        this.filteredRecipesIsEmpty = true;
      } else {
        this.filteredRecipesIsEmpty = false;
      }
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

  myIngredientsToArray() {
    for (const ingr of this.myIngredients) {
      this.myIngredientsArray.push(ingr.name);
    }
  }

  onToggle(event) {
    this.fridgeIngredientsOnly = event.checked;
    this.fridgeIsEmpty = false;
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
