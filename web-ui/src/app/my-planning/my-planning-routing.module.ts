import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyPlanningComponent } from './my-planning.component';

const routes: Routes = [
  { path: 'my-planning', component: MyPlanningComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyPlanningRoutingModule { }
