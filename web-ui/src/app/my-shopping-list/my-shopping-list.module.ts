import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyShoppingListRoutingModule } from './my-shopping-list-routing.module';
import { MyShoppingListComponent } from './my-shopping-list.component';

@NgModule({
  imports: [
    CommonModule,
    MyShoppingListRoutingModule
  ],
  declarations: [MyShoppingListComponent]
})
export class MyShoppingListModule { }
