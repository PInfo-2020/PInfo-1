import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyShoppingListComponent } from './my-shopping-list.component';

const routes: Routes = [
  { path: 'my-shopping-list', component: MyShoppingListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyShoppingListRoutingModule { }
