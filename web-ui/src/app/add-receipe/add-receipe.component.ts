import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-receipe',
  templateUrl: './add-receipe.component.html',
  styleUrls: ['./add-receipe.component.css']
})
export class AddReceipeComponent implements OnInit {

  constructor() { }

  onNameChanged(nameEntered: string) {
    console.log('nom : ', nameEntered);
  }

  ngOnInit() {
  }

}
