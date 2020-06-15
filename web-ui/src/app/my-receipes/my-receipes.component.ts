import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';

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

@Component({
  selector: 'app-my-receipes',
  templateUrl: './my-receipes.component.html',
  styleUrls: ['./my-receipes.component.css']
})

export class MyReceipesComponent implements OnInit {
  constructor(public keycloak: KeycloakService, private http: HttpClient) { }
  public recipeList: Array<SimpleRecipe> = [];

  addJsonToClass(json) {
    let recipe;

    for (const userRecipe of json) {
      // tslint:disable-next-line: max-line-length
      recipe = new SimpleRecipe(userRecipe.name, userRecipe.preparationTime, userRecipe.difficulty, userRecipe.picture, userRecipe.grade, userRecipe.id);
      this.recipeList.push(recipe);
    }
  }

  getUserRecipes() {
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
           rejectUnauthorized: 'false' })
      };
    this.http.get('api/v1/recipe/user', headernode).toPromise().then(json => {
      this.addJsonToClass(json);
    });
  }

  redirectToRecipe(recipeId) {
    // A la place du print, rediriger vers la page de la recette
    console.log(recipeId);
  }

  ngOnInit() {
    this.getUserRecipes();
  }

}
