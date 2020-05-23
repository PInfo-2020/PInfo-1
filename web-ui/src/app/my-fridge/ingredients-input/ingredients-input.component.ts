import { NgModule, AfterViewInit } from '@angular/core';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { NON_BINDABLE_ATTR } from '@angular/compiler/src/render3/view/util';

class AddedIngredient {
  name = '';
  quantity = '';
  unity = '';
  id = '';
  constructor(name, quantity, id, unity) {
      this.name = name;
      this.quantity = quantity;
      this.id = id;
      this.unity = unity;
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

  public SelectedAssetFromSTCombo(ingre) {
    if (!this.listIngredient.includes(ingre)) {
      return;
    }

    console.log(ingre);

    let alreadyIn = 0;

    for (const ingredient of this.addedIngredients) {
      if (ingredient.name === ingre) {
        alreadyIn = 1;
      }
    }
    let newIngr;

    if (alreadyIn === 0)  {
      for (const ingred of this.ingredients) {
        if (ingred.name === ingre) {
          console.log('Ingred : ', ingred);
          newIngr = new AddedIngredient(ingred.name, 0, ingred.id, ingred.unity);
          this.addedIngredients.push(newIngr);
        }
      }
    }
    console.log( 'Added Ingredients :' );
    console.log(this.addedIngredients);
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
    const headernode = {
      headers: new HttpHeaders(
          { Accept: 'application/json' ,
           rejectUnauthorized: 'false' })
      };
    this.http.get(this.url, headernode).toPromise().then(json => {
      console.log(json);
      this.addJsonToClass(json);
    });
  }

    /*
    let post_message = data;
    let header_node = {
        headers: new HttpHeaders(
            { 'Accept': 'application/json' },
            { 'rejectUnauthorized': 'false' })
        };

    return this.http.post('https://ip/createdata', post_message, header_node).toPromise();
}
  */

  addJsonToClass(json) {
    let ingr;

    for (const ingredient of json) {
      ingr = new Ingredient(ingredient[0], ingredient[1], ingredient[2]);
      this.ingredients.push(ingr);
    }
    for (const ingredient of this.ingredients) {
      this.listIngredient.push(ingredient.name);
    }
  }


  onRemove(index) {
    console.log('index : ', index);
    console.log('this.addedIngredients : ', this.addedIngredients[index]);
    this.addedIngredients.splice(index, 1);
  }

 }


