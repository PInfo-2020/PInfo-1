import { Component, OnInit } from '@angular/core';
import { KeycloakService } from '../services/keycloak/keycloak.service';

@Component({
  selector: 'app-my-planning',
  templateUrl: './my-planning.component.html',
  styleUrls: ['./my-planning.component.css']
})
export class MyPlanningComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  ngOnInit() {
  }

}
