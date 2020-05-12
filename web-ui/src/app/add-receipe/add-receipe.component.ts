import { Component, OnInit } from '@angular/core';
import { numberSymbols } from '@progress/kendo-angular-intl';

interface Ingredients {
  [Key: string]: number;
}

interface Recipe {
  name: string;
  picture: string;
  people: number;
  time: number;
  difficulty: number;
  ingredients: Ingredients;
  preparation: string;
  userID: number;
}

@Component({
  selector: 'app-add-receipe',
  templateUrl: './add-receipe.component.html',
  styleUrls: ['./add-receipe.component.css']
})

export class AddReceipeComponent implements OnInit {

  constructor() { }

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
      time: this.timeEntered,
      difficulty: this.difficultyEntered,
      ingredients: this.ingredientsEntered,
      preparation: this.recipeEntered,
      userID: 1                               // for testing
    };
    this.json = JSON.stringify(this.recipe);
    console.log(this.json);
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
