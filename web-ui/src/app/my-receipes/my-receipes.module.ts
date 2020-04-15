import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyReceipesRoutingModule } from './my-receipes-routing.module';
import { MyReceipesComponent } from './my-receipes.component';

@NgModule({
  imports: [
    CommonModule,
    MyReceipesRoutingModule
  ],
  declarations: [MyReceipesComponent]
})
export class MyReceipesModule { }
