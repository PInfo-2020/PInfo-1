import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ViewRecipeRoutingModule } from './view-recipe-routing.module';
import { ViewRecipeComponent } from './view-recipe.component';

@NgModule({
  imports: [
    CommonModule,
    ViewRecipeRoutingModule
  ],
  declarations: [ViewRecipeComponent]
})
export class ViewRecipeModule { }
