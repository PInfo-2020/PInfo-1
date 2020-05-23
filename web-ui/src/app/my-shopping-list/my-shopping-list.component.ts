import { Component, OnInit } from '@angular/core';
import { KeycloakService } from '../services/keycloak/keycloak.service';

@Component({
  selector: 'app-my-shopping-list',
  templateUrl: './my-shopping-list.component.html',
  styleUrls: ['./my-shopping-list.component.css']
})
export class MyShoppingListComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  ngOnInit() {
  }

}
