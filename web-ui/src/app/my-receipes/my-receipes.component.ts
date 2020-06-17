import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
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

@Component({
  selector: 'app-my-receipes',
  templateUrl: './my-receipes.component.html',
  styleUrls: ['./my-receipes.component.css']
})

export class MyReceipesComponent implements OnInit {
  constructor(public keycloak: KeycloakService, private http: HttpClient, private router: Router) { }
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

  ngOnInit() {
    this.getUserRecipes();
  }

}
