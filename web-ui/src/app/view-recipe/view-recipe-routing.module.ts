import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewRecipeComponent } from './view-recipe.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';


const routes: Routes = [
  { path: 'view-recipe', component: ViewRecipeComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule
  ],
  exports: [
    RouterModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule
  ]
})
export class ViewRecipeRoutingModule { }
