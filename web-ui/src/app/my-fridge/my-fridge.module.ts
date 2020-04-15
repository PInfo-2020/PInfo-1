import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MyFridgeRoutingModule } from './my-fridge-routing.module';
import { MyFridgeComponent } from './my-fridge.component';

@NgModule({
  imports: [
    CommonModule,
    MyFridgeRoutingModule
  ],
  declarations: [MyFridgeComponent]
})
export class MyFridgeModule { }

