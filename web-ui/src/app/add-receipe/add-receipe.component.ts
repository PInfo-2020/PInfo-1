import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-receipe',
  templateUrl: './add-receipe.component.html',
  styleUrls: ['./add-receipe.component.css']
})
export class AddReceipeComponent implements OnInit {

  constructor() { }

  onNameChanged(nameEntered: string) {
    console.log('Nom : ', nameEntered);
  }

  onTimeChanged(timeEntered: number) {
    console.log('Temps : ', timeEntered);
  }

  onDifficultyChanged(difficultyEntered: number) {
    console.log('Difficulté : ', difficultyEntered);
  }

  onRecipeChanged(recipeEntered: string) {
    console.log('Recette : ', recipeEntered);
  }

  onPeopleChanged(peopleEntered: number) {
    console.log('Nombre de personnes : ', peopleEntered);
  }

  ngOnInit() {
  }

}
