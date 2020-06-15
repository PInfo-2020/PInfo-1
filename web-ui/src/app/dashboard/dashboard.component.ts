import { DifficultyInputComponent } from './../add-receipe/difficulty-input/difficulty-input.component';
import { numberSymbols } from '@progress/kendo-angular-intl';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpHeaders, HttpClient } from '@angular/common/http';

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

export class DashboardComponent implements OnInit, AfterViewInit {

  constructor(private http: HttpClient, public keycloak: KeycloakService) { }

  public recipes: Array<Recipe> = [];

  public filteredRecipes: Array<Recipe> = [];

  public ingredients: Array<Ingredient> = [];

  public myIngredients: Array<Ingredient> = [];

  public myIngredientsArray: Array<string> = [];

  public keyword: string = '';

  url = 'api/v1/recipe/recipes';

  ngOnInit() {
    this.getRecipes();
    this.test();
    this.filteredRecipes = this.recipes;
    // this.onSearch('test');
  }

  ngAfterViewInit() {

  }

  getRecipes() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin':'*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.url, headernode).toPromise().then(json => {
      console.log(json);
    });
  }

  onClick(i) {
    console.log(i);
  }


  changeKeyword(kw: string) {
    this.keyword = kw;
  }

  onSearch() {

    console.log(this.keyword);
    this.filteredRecipes = [];
    // let recip;
    if (this.keyword === '') {
      this.filteredRecipes = this.recipes;
    } else {
      for (const recip of this.recipes) {
        if (recip.name === this.keyword) {
          this.filteredRecipes.push(recip);
        }
        for (const ingr of recip.ingredients) {
          if (ingr.name === this.keyword) {
            this.filteredRecipes.push(recip);
          }
        }
      }
    }

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

  test() {
    let recep;
    let ingr;

    ingr = new Ingredient(12, 'tomate');
    this.ingredients.push(ingr);
    ingr = new Ingredient(22, 'fromage');
    this.ingredients.push(ingr);
    recep = new Recipe(12, 'pizza', 20, 5, this.ingredients);
    this.recipes.push(recep);
    console.log(this.recipes);
    this.ingredients = [];

    ingr = new Ingredient(93, 'pates');
    this.ingredients.push(ingr);
    ingr = new Ingredient(54, 'salade');
    this.ingredients.push(ingr);
    recep = new Recipe(34, 'pasta', 30, 2, this.ingredients);
    this.recipes.push(recep);
    this.ingredients = [];

    ingr = new Ingredient(36, 'pomme de terre');
    this.ingredients.push(ingr);
    ingr = new Ingredient(28, 'tomate');
    this.ingredients.push(ingr);
    recep = new Recipe(63, 'gaspaccio', 50, 8, this.ingredients);
    this.recipes.push(recep);
    console.log(this.recipes);
    this.ingredients = [];

    ingr = new Ingredient(12, 'tomate');
    this.myIngredients.push(ingr);
    ingr = new Ingredient(43, 'carotte');
    this.myIngredients.push(ingr);
    ingr = new Ingredient(43, 'fromage');
    this.myIngredients.push(ingr);
  }

}
