import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { KeycloakService } from 'src/app/services/keycloak/keycloak.service';

@Component({
  selector: 'app-difficulty-input',
  templateUrl: './difficulty-input.component.html',
  styleUrls: ['./difficulty-input.component.css']
})
export class DifficultyInputComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  @Output() changedDifficulty = new EventEmitter<string>();

  changeDifficulty(difficulty: string) {
    this.changedDifficulty.emit(difficulty);
  }

  ngOnInit() {
  }

}
