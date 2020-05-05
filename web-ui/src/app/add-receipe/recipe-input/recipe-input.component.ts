import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-recipe-input',
  templateUrl: './recipe-input.component.html',
  styleUrls: ['./recipe-input.component.css']
})
export class RecipeInputComponent implements OnInit {

  constructor() { }

  @Output() changedRecipe = new EventEmitter<string>();

  changeRecipe(recipeName: string) {
    this.changedRecipe.emit(recipeName);
  }

  ngOnInit(): void {
  }

}
