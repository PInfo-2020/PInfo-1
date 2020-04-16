import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyFavoritesRoutingModule } from './my-favorites-routing.module';
import { MyFavoritesComponent } from './my-favorites.component';

@NgModule({
  imports: [
    CommonModule,
    MyFavoritesRoutingModule
  ],
  declarations: [MyFavoritesComponent]
})
export class MyFavoritesModule { }
