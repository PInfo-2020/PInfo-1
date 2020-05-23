import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { KeycloakService } from 'src/app/services/keycloak/keycloak.service';

@Component({
  selector: 'app-time-input',
  templateUrl: './time-input.component.html',
  styleUrls: ['./time-input.component.css']
})
export class TimeInputComponent implements OnInit {

  constructor(public keycloak: KeycloakService) { }

  @Output() changedTime = new EventEmitter<string>();

  changeTime(preparationTime: string) {
    this.changedTime.emit(preparationTime);
  }


  ngOnInit() {
  }

}
