import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-difficulty-input',
  templateUrl: './difficulty-input.component.html',
  styleUrls: ['./difficulty-input.component.css']
})
export class DifficultyInputComponent implements OnInit {

  constructor() { }

  @Output() changedDifficulty = new EventEmitter<string>();

  changeDifficulty(difficulty: string) {
    this.changedDifficulty.emit(difficulty);
  }

  ngOnInit() {
  }

}
