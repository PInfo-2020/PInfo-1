import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyPlanningRoutingModule } from './my-planning-routing.module';
import { MyPlanningComponent } from './my-planning.component';

@NgModule({
  imports: [
    CommonModule,
    MyPlanningRoutingModule
  ],
  declarations: [MyPlanningComponent]
})
export class MyPlanningModule { }

