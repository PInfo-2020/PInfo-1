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

class DataVerification {
  name = true;
  time = true;
  difficulty = true;
  people = true;
  ingredients = true;
  preparation = true;
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
  json: string;
  dataVerification = new DataVerification();

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
    for (const booleanValue in this.dataVerification) {
      this.dataVerification[booleanValue] = true;
    }
    // Recipe can not be empty
    if (! this.nameEntered || ! this.nameEntered.trim()) {
      this.dataVerification.name = false;
    }

    // Time has to be positive integer and non zero
    if (!this.isInteger(this.timeEntered) || this.timeEntered <= 0) {
      this.dataVerification.time = false;
    }

    // Difficulty must be an integer between 1 and 10
    if (!this.isInteger(this.difficultyEntered) || this.difficultyEntered < 1 || this.difficultyEntered > 10) {
      this.dataVerification.difficulty = false;
    }

    // People has to be positive integer and non zero
    if (!this.isInteger(this.peopleEntered) || this.peopleEntered <= 0) {
      this.dataVerification.people = false;
    }

    // There has to be ingredients
    if (! this.ingredientsEntered || ! this.areIngredientsQuantitiesLegit()) {
      this.dataVerification.ingredients = false;
    }

    // Recipe can not be empty
    if (! this.recipeEntered || ! this.recipeEntered.trim()) {
      this.dataVerification.preparation = false;
    }

    for (const booleanValue in this.dataVerification) {
      if (!this.dataVerification[booleanValue]) {
        return false;
      }
    }
    return true;
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
    //tslint:disable-next-line: max-line-length
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
        // Rediriger vers la page de la recette !
      }
    });
  }

  publishRecipe() {
    if (this.verifyData()) {
      this.createJSON();
    } else {
      alert('Il y a au moins une erreur dans les données que vous avez entré, veuillez corriger chaque input où vous voyez une petit icone');
    }
  }

  ngOnInit() {
  }

}
