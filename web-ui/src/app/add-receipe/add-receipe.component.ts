import { Component, OnInit } from '@angular/core';
import { numberSymbols } from '@progress/kendo-angular-intl';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { KeycloakService } from 'keycloak-angular';

interface Recipe {
  name: string;
  picture: string;
  people: number;
  preparationTime: number;
  difficulty: number;
  ingredients: Array<IngredientToBeStringified>;
  preparation: string;
}

class IngredientToBeStringified {
    quantity: number;
    detailsID: number;
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
  ingredientsEntered: Array<IngredientToBeStringified>;
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

  onIngredientsChanged(jsonIngredient: Array<IngredientToBeStringified>) {
      this.ingredientsEntered = jsonIngredient;
  }

  isInteger(value) {
    let x;
    if (isNaN(value)) {
      return false;
    }
    x = parseFloat(value);
    return (x | 0) === x;
  }

  areIngredientsQuantitiesLegit() {
    for (const ingredient of this.ingredientsEntered) {
      if (! this.isInteger(ingredient.quantity) || ingredient.quantity <= 0) {
        return false;
      }
    }
    return true;
  }

  verifyData() {
    this.incorrectData = 0;
    
    // Recipe can not be empty
    if (! this.nameEntered || ! this.nameEntered.trim()) {
      console.log('name error');
      this.incorrectData = 1;
    }

    // Time has to be positive integer and non zero
    if (!this.isInteger(this.timeEntered) || this.timeEntered <= 0) {
      console.log('time error');
      this.incorrectData = 1;
    }

    // Difficulty must be an integer between 1 and 10
    if (!this.isInteger(this.difficultyEntered) || this.difficultyEntered < 1 || this.difficultyEntered > 10) {
      console.log('difficulty error');
      this.incorrectData = 1;
    }

    // People has to be positive integer and non zero
    if (!this.isInteger(this.peopleEntered) || this.peopleEntered <= 0) {
      console.log('people erro');
      this.incorrectData = 1;
    }

    // There has to be ingredients
    if (! this.ingredientsEntered || ! this.areIngredientsQuantitiesLegit()) {
      console.log('ingr error');
      this.incorrectData = 1;
    }

    // Recipe can not be empty
    if (! this.recipeEntered || ! this.recipeEntered.trim()) {
      console.log('recipe error');
      this.incorrectData = 1;
    }

    console.log('is not ok ? :', this.incorrectData);
  }

  createJSON() {
    this.recipe = {
      name: this.nameEntered,
      picture: this.pictureEntered,
      people: this.peopleEntered,
      preparationTime: this.timeEntered,
      difficulty: this.difficultyEntered,
      ingredients: this.ingredientsEntered,
      preparation: this.recipeEntered                              // for testing
    };
    this.json = JSON.stringify(this.recipe);
    console.log(this.json);
    //tslint:disable-next-line: max-line-length
    /*
    this.http.post('api/v1/recipe', this.json, {
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
    */
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
