import { Component, OnInit } from '@angular/core';
import { identifierModuleUrl } from '@angular/compiler';
import { StringMap } from '@angular/compiler/src/compiler_facade_interface';
import { HttpHeaders, HttpClient } from '@angular/common/http';

class Recipe{
  id: number;
  name: string;
  picture: null;
  people: number;
  preparationTime: number;
  difficulty: number;
  ingredients: Array<IngredientRecipe>;
  preparation: string;
  author: string;
  publicationDate: number;
  grade: number;
  comments: Array<string>;
  constructor(id, name, picture, people, preparationTime, difficulty, ingredients, preparation, author, publicationDate, grade, comments) {
    this.id = id;
    this.name = name;
    this.picture = picture;
    this.people = people;
    this.preparationTime = preparationTime;
    this.difficulty = difficulty;
    this.ingredients = ingredients;
    this.preparation = preparation;
    this.author = author;
    this.publicationDate = publicationDate;
    this.grade = grade;
    this.comments = comments;
  }
}

class Ingredient {
  id: number;
  name: string;
  unity: string;
  constructor(id, name, unity) {
    this.id = id;
    this.name = name;
    this.unity = unity;
  }
}

class IngredientRecipe {
  id: number;
  name: string;
  unity: string;
  quantity: number;
  constructor(id, name, unity, quantity) {
    this.id = id;
    this.name = name;
    this.unity = unity;
    this.quantity = quantity;
  }
}

@Component({
  selector: 'app-view-recipe',
  templateUrl: './view-recipe.component.html',
  styleUrls: ['./view-recipe.component.css']
})

export class ViewRecipeComponent implements OnInit {

  constructor(private http: HttpClient) { }

  public recipes: Array<Recipe> = [];

  public allIngredients: Array<Ingredient> = [];

  // public recipeIngredients: Array<IngredientRecipe> = [];

  // tslint:disable-next-line: max-line-length
  // public recipes = '[{"id":1, "name":"Cake choco", "picture":null, "people":4, "preparationTime":10, "difficulty":7, "ingredients":[{"id":1,"quantity":100,"detailsID":99}, {"id":2,"quantity":100,"detailsID":39}, {"id":3,"quantity":2,"detailsID":330}], "preparation":"Mélange et bouffe salope", "author":"8f59174b-bb06-46de-8782-cc9a49d7a6c5", "publicationDate":1592179200000, "grade":-1, "comments":[]}, {"id":2, "name":"Salade de fruits", "picture":null, "people":4, "preparationTime":5, "difficulty":1, "ingredients":[{"id":4,"quantity":100,"detailsID":28}, {"id":5,"quantity":100,"detailsID":201}, {"id":6,"quantity":5,"detailsID":313}], "preparation":"Salade de fruits, jolie, jolie, jolie ...", "author":"8f59174b-bb06-46de-8782-cc9a49d7a6c5","publicationDate":1592179200000, "grade":-1, "comments":[]}]';

  // tslint:disable-next-line: max-line-length
  public recipesJson = [{id: 1, name: 'Cake choco', picture: null, people: 4, preparationTime: 10, difficulty: 7, ingredients: [{id: 1, quantity: 100, detailsID: 99}, {id: 2, quantity: 10, detailsID: 35}], preparation: 'dwawadwwadaw', author: '8f59174b-bb06-46de-8782-cc9a49d7a6c5', publicationDate: 1592179200000, grade: -1, comments: []}, {id: 2, name: 'Cake choco 2', picture: null, people: 4, preparationTime: 10, difficulty: 7, ingredients: [{id: 3, quantity: 200, detailsID: 99}, {id: 4, quantity: 20, detailsID: 35}], preparation: 'dwawadwwadaw\ndwmawdmawoowa', author: '8f59174b-bb06-46de-8782-cc9a49d7a6c5', publicationDate: 1592179200000, grade: -1, comments: []}, {id: 3, name: 'Cake choco 2', picture: null, people: 4, preparationTime: 10, difficulty: 7, ingredients: [{id: 5, quantity: 200, detailsID: 99}, {id: 6, quantity: 20, detailsID: 35}], preparation: 'dwawadwwadaw\ndwmawdmawoowa', author: '8f59174b-bb06-46de-8782-cc9a49d7a6c5', publicationDate: 1592179200000, grade: -1, comments: []}];

  urlIngredients = 'api/v1/ingredients/minInfos';

  ngOnInit() {
    // console.log(this.recipesJson);
    this.getIngredients();
    // console.log(this.allIngredients);
    this.test();
    // this.jsonRecipeToArray(this.recipesJson);


  }

  getIngredients() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.urlIngredients, headernode).toPromise().then(json => {
      this.addJsonAllIngredientToClass(json);

    });
  }

  addJsonAllIngredientToClass(json) {
    let ingr;

    for (const ingredient of json) {
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
      this.allIngredients.push(ingr);
    }
    // console.log(this.allIngredients);
  }

  jsonRecipeToArray(js) {
    let ingredientRecipe: Array<IngredientRecipe> = [];

    for (const recipe of js) {
      // console.log(recipe.ingredients);
      ingredientRecipe = this.ingredientsRecipeToArray(recipe.ingredients);
      console.log(ingredientRecipe);
    }
  }

  ingredientsRecipeToArray(js) {
    let ingr;
    let id;
    let name;
    let unity;
    let quantity;
    let flag = 0;
    let count;

    let ingredientRecipe: Array<IngredientRecipe> = [];
    console.log('all ingredients');
    console.log(this.allIngredients);
    for (const ingredient of js) {
      id = ingredient[0];
      quantity = ingredient[1];
      /*while (flag < 1) {
        console.log(this.allIngredients[0]);
        if (this.allIngredients[count].id === id) {
          name = this.allIngredients[count].name;
          unity = this.allIngredients[count].unity;
          flag = 1;
        }
        count++;
      }*/
      console.log('all ingr');
      ingr = new IngredientRecipe(id, name, unity, quantity);
      ingredientRecipe.push(ingr);
    }
    return ingredientRecipe;
  }

  test() {
    console.log(this.allIngredients[0]);
    for (const ingr of this.allIngredients) {
      console.log('tes');
      console.log(ingr);
    }
  }
}
