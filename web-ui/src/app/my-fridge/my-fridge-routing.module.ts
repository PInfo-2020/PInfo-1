import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyFridgeComponent } from './my-fridge.component';

const routes: Routes = [
  { path: 'my-fridge', component: MyFridgeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyFridgeRoutingModule { }
