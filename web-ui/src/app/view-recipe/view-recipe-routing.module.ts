import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewRecipeComponent } from './view-recipe.component';

const routes: Routes = [
  { path: 'view-recipe', component: ViewRecipeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ViewRecipeRoutingModule { }
