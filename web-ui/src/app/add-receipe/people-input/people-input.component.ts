import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-people-input',
  templateUrl: './people-input.component.html',
  styleUrls: ['./people-input.component.css']
})
export class PeopleInputComponent implements OnInit {

  constructor() { }


  @Output() changedPeople = new EventEmitter<string>();

  changePeople(people: string) {
    this.changedPeople.emit(people);
  }

  ngOnInit(): void {
  }

}
