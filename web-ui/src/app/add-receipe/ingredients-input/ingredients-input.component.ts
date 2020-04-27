import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable, BehaviorSubject, from } from 'rxjs';
import { delay, switchMap, map, tap, startWith } from 'rxjs/operators';
import {FormControl} from '@angular/forms';

export interface State {
  flag: string;
  name: string;
  population: string;
}

@Component({
  selector: 'app-ingredients-input',
  templateUrl: './ingredients-input.component.html',
  styleUrls: ['./ingredients-input.component.css']
})

export class IngredientsInputComponent implements OnInit {

  /* @ViewChild('list') list;

    public source: Array<{ text: string, value: number }> = [
        { text: 'Small', value: 1 },
        { text: 'Medium', value: 2 },
        { text: 'Large', value: 3 }
    ];

    public data: Array<{ text: string, value: number }>;

    constructor() {
        this.data = this.source.slice();
    }

    ngOnInit() {
    }

    ngAfterViewInit() {
      const contains = value => s => s.text.toLowerCase().indexOf(value.toLowerCase()) !== -1;

      this.list.filterChange.asObservable().pipe(
            switchMap(value => from([this.source]).pipe(
                tap(() => this.list.loading = true),
                delay(1000),
                map((data) => data.filter(contains(value)))
            ))
        )
        .subscribe(x => {
            this.data = x;
            this.list.loading = false;
        });
    } */

    /* States - Material*/

    stateCtrl = new FormControl();
    filteredStates: Observable<State[]>;

    states: State[] = [
      {
        name: 'Arkansas',
        population: '2.978M',
        // https://commons.wikimedia.org/wiki/File:Flag_of_Arkansas.svg
        flag: 'https://upload.wikimedia.org/wikipedia/commons/9/9d/Flag_of_Arkansas.svg'
      },
      {
        name: 'California',
        population: '39.14M',
        // https://commons.wikimedia.org/wiki/File:Flag_of_California.svg
        flag: 'https://upload.wikimedia.org/wikipedia/commons/0/01/Flag_of_California.svg'
      },
      {
        name: 'Florida',
        population: '20.27M',
        // https://commons.wikimedia.org/wiki/File:Flag_of_Florida.svg
        flag: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Flag_of_Florida.svg'
      },
      {
        name: 'Texas',
        population: '27.47M',
        // https://commons.wikimedia.org/wiki/File:Flag_of_Texas.svg
        flag: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Flag_of_Texas.svg'
      }
    ];

    constructor() {
      this.filteredStates = this.stateCtrl.valueChanges
        .pipe(
          startWith(''),
          map(state => state ? this._filterStates(state) : this.states.slice())
        );
    }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

    private _filterStates(value: string): State[] {
      const filterValue = value.toLowerCase();

      return this.states.filter(state => state.name.toLowerCase().indexOf(filterValue) === 0);
    }

     share() {
      window.alert('The product has been shared!');
    }





}
