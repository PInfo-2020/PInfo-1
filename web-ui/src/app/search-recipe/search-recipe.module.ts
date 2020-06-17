import { NgModule, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SearchRecipeRoutingModule } from './search-recipe-routing.module';
import { SearchRecipeComponent } from './search-recipe.component';

import { MatCheckboxModule } from '@angular/material/checkbox';

@NgModule({
  imports: [
    CommonModule,
    SearchRecipeRoutingModule,
    MatCheckboxModule,
  ],
  declarations: [
    SearchRecipeComponent,
  ]
})

export class SearchRecipeModule{

}

/*MatFormFieldModule,
MatInputModule,
MatButtonModule*/
