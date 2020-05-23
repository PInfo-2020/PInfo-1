import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { KeycloakService } from 'src/app/services/keycloak/keycloak.service';

@Component({
  selector: 'app-recipe-input',
  templateUrl: './recipe-input.component.html',
  styleUrls: ['./recipe-input.component.css']
})
export class RecipeInputComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  @Output() changedRecipe = new EventEmitter<string>();

  changeRecipe(recipeName: string) {
    this.changedRecipe.emit(recipeName);
  }

  ngOnInit(): void {
  }

}
