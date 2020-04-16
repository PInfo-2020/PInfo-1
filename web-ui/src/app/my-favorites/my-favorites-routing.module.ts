import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyFavoritesComponent } from './my-favorites.component';

const routes: Routes = [
  { path: 'my-favorites', component: MyFavoritesComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyFavoritesRoutingModule { }
