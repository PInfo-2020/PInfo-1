import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-my-fridge',
  templateUrl: './my-fridge.component.html',
  styleUrls: ['./my-fridge.component.css']
})
export class MyFridgeComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  ngOnInit() {
  }

}
