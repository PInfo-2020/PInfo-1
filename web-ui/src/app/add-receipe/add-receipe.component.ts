import { Component, OnInit } from '@angular/core';
import { numberSymbols } from '@progress/kendo-angular-intl';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { KeycloakService } from 'keycloak-angular';

interface Ingredients {
  [Key: string]: number;
}

interface Recipe {
  name: string;
  picture: string;
  people: number;
  preaprationTime: number;
  difficulty: number;
  ingredients: Ingredients;
  preparation: string;
}

@Component({
  selector: 'app-add-receipe',
  templateUrl: './add-receipe.component.html',
  styleUrls: ['./add-receipe.component.css']
})

export class AddReceipeComponent implements OnInit {

  constructor(public keycloak: KeycloakService, private http: HttpClient) { }

  recipe: Recipe;
  nameEntered: string;
  pictureEntered: string;
  timeEntered: number;
  recipeEntered: string;
  difficultyEntered: number;
  ingredientsEntered: Ingredients;
  peopleEntered: number;
  incorrectData: number;
  json: string;

  onNameChanged(nameEntered: string) {
    this.nameEntered = nameEntered;
  }

  onTimeChanged(timeEntered: number) {
    this.timeEntered = timeEntered;
  }

  onDifficultyChanged(difficultyEntered: number) {
    this.difficultyEntered = difficultyEntered;
  }

  onRecipeChanged(recipeEntered: string) {
    this.recipeEntered = recipeEntered;
  }

  onPeopleChanged(peopleEntered: number) {
    this.peopleEntered = peopleEntered;
  }

  verifyData() {
    this.incorrectData = 0;
  }

  createJSON() {
    this.recipe = {
      name: this.nameEntered,
      picture: this.pictureEntered,
      people: this.peopleEntered,
      preaprationTime: this.timeEntered,
      difficulty: this.difficultyEntered,
      ingredients: this.ingredientsEntered,
      preparation: this.recipeEntered                              // for testing
    };
    this.json = JSON.stringify(this.recipe);
    console.log(this.json);
    // tslint:disable-next-line: max-line-length
    // this.http.post('api/v1/recipe', tempJson, {
    //   headers: new HttpHeaders(
    //     {'Access-Control-Allow-Origin':'*',
    //     'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    //      rejectUnauthorized: 'false' }),
    //   reportProgress: true,
    //   observe: 'events'
    // }).subscribe(events => {
    //   if (events.type === HttpEventType.Response) {
    //     console.log(events.body);
    //     alert('SUCCESS !!');
    //   }

    // });
  }

  printErrors() {

  }

  publishRecipe() {
    this.verifyData();
    if (this.incorrectData === 0) {
      this.createJSON();
    } else {
      this.printErrors();
    }
  }

  ngOnInit() {
  }

}
