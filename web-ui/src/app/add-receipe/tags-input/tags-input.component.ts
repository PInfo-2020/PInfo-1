import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-tags-input',
  templateUrl: './tags-input.component.html',
  styleUrls: ['./tags-input.component.css']
})


export class TagsInputComponent implements OnInit {

  constructor(public keycloak: KeycloakService) {}

  public listItems: Array<string> = [
    'Vegan',
    'Végétarien',
    'Sans Gluten'];

  public value: any = [];

  ngOnInit() {}


}
