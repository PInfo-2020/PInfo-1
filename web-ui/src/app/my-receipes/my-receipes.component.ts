import { Component, OnInit } from '@angular/core';
import { KeycloakService } from './../services/keycloak/keycloak.service';
@Component({
  selector: 'app-my-receipes',
  templateUrl: './my-receipes.component.html',
  styleUrls: ['./my-receipes.component.css']
})
export class MyReceipesComponent implements OnInit {



  constructor(public keycloak: KeycloakService) { }

  ngOnInit() {
 
  }

}
