import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns'

import { AddReceipeRoutingModule } from './add-receipe-routing.module';
import { AddReceipeComponent } from './add-receipe.component';

@NgModule({
  imports: [
    CommonModule,
    AddReceipeRoutingModule,
    HttpClientModule,
    BrowserModule,
    DropDownsModule,
  ],
  declarations: [AddReceipeComponent],
  exports: [DropDownsModule]
})
export class AddReceipeModule { }
