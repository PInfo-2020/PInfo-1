import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

import { AddReceipeRoutingModule } from './add-receipe-routing.module';
import { AddReceipeComponent } from './add-receipe.component';

@NgModule({
  imports: [
    CommonModule,
    AddReceipeRoutingModule,
    HttpClientModule
  ],
  declarations: [AddReceipeComponent]
})
export class AddReceipeModule { }
