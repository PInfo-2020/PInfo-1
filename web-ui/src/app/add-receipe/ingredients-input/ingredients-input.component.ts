import { NgModule, AfterViewInit } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { NON_BINDABLE_ATTR } from '@angular/compiler/src/render3/view/util';

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

  public listIngredient: Array<string> = [];

  public ingredients: Array<Ingredient> = [];

  public data: Array<string> = [];

  public addedIngredients: Array<AddedIngredient> = [];

  // private json: Array<Array<string>>;

  url = 'https://www.pickncook.ch/api/v1/ingredients/minInfos';

  public SelectedAssetFromSTCombo(e) {
    if (!this.listIngredient.includes(e)) {
      return;
    }
    console.log(e);
  }

  ngOnInit() {
    this.getIngredients();
  }

  ngAfterViewInit() {
    const contains = value => s => s.toLowerCase().indexOf(value.toLowerCase()) !== -1;

    this.list.filterChange.asObservable().switchMap(value => Observable.from([this.listIngredient])
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
      this.addJsonToClass(json);
    });
  }

  addJsonToClass(e) {
    let ingr;

    for (const ingredient of e){
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
      this.ingredients.push(ingr);
    }
    for (const ingredient of this.ingredients) {
      this.listIngredient.push(ingredient.name);
    }
  }

}


