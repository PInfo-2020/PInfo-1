import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { SearchRecipeRoutingModule } from './search-recipe-routing.module';
import { SearchRecipeComponent } from './search-recipe.component';

@NgModule({
  imports: [
    CommonModule,
    SearchRecipeRoutingModule,
  ],
  declarations: [
    SearchRecipeComponent,
  ]
})
export class SearchRecipeModule { }

/*MatFormFieldModule,
MatInputModule,
MatButtonModule*/
