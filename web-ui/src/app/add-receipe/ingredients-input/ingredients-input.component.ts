import { NgModule, AfterViewInit } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType } from '@angular/common/http';

class AddedIngredient {
  name = '';
  quantity = '';
  id = '';
  constructor(name, quantity, id) {
      this.name = name;
      this.quantity = quantity;
      this.id = id;
  }
}

class Ingredient {
  id = '';
  name = '';
  unity = '';
  constructor(id, name, unity) {
      this.id = id;
      this.name = name;
      this.unity = unity;
  }
}

@Component({
  selector: 'app-ingredients-input',
  templateUrl: './ingredients-input.component.html',
  styleUrls: ['./ingredients-input.component.css']
})


export class IngredientsInputComponent implements OnInit, AfterViewInit {

  @ViewChild('list') list;

  constructor(private http: HttpClient) { }

  public source: Array<string> = ['Albania', 'Andorra', 'Armenia', 'Austria', 'Azerbaijan'];

  public ingredients: Array<Ingredient> ;

  public data: Array<string> = [];

  public addedIngredients: Array<AddedIngredient> = [];

  public temp;


  // private json: Array<Array<string>>;

  url = 'https://www.pickncook.ch/api/v1/ingredients/minInfos';

  public SelectedAssetFromSTCombo(e) {
    if (!this.source.includes(e)) {
      return;
    }
    console.log(e);
  }

  ngOnInit() {
    this.getIngredients();
  }

  ngAfterViewInit() {
    const contains = value => s => s.toLowerCase().indexOf(value.toLowerCase()) !== -1;

    this.list.filterChange.asObservable().switchMap(value => Observable.from([this.source])
      .do(() => this.list.loading = true)
      .map((data) =>  data.filter(contains(value))))
      .subscribe(x => {
          this.data = x;
          this.list.loading = false;
      });
  }

  addIngredient(name) { }

  getIngredients() {
    this.http.get(this.url).toPromise().then(json => {
      console.log(json);
      // this.addJsonToClass(json);
    });
  }

  addJsonToClass(e) {
    this.temp = new Ingredient(e[0][0], e[0][1], e[0][2]);
    this.ingredients.push(this.temp);
    console.log(this.temp);
    this.temp = new Ingredient(e[1][0], e[1][1], e[1][2]);
    // this.ingredients.push(this.temp);
    console.log(e.length);
    // console.log(this.ingredients);

  }

}


