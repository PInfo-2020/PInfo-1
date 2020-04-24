import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-time-input',
  templateUrl: './time-input.component.html',
  styleUrls: ['./time-input.component.css']
})
export class TimeInputComponent implements OnInit {

  constructor() { }

  @Output() changedTime = new EventEmitter<string>();

  changeTime(preparationTime: string) {
    this.changedTime.emit(preparationTime);
  }


  ngOnInit() {
  }

}
