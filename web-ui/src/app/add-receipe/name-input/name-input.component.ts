import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-name-input',
  templateUrl: './name-input.component.html',
  styleUrls: ['./name-input.component.css']
})
export class NameInputComponent implements OnInit {

  constructor() { }

  @Output() changedName = new EventEmitter<string>();

  changeName(recipeName: string) {
    this.changedName.emit(recipeName);
  }

  ngOnInit() {
  }


}
