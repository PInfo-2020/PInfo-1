import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-name-input',
  templateUrl: './name-input.component.html',
  styleUrls: ['./name-input.component.css']
})
export class NameInputComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  @Output() changedName = new EventEmitter<string>();

  changeName(recipeName: string) {
    this.changedName.emit(recipeName);
  }

  ngOnInit() {
  }


}
