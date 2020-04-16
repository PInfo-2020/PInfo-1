import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddReceipeComponent } from './add-receipe.component';

const routes: Routes = [
  { path: 'add-receipe', component: AddReceipeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddReceipeRoutingModule { }
