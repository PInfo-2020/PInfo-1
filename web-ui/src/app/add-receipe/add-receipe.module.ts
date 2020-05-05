import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';


import { AddReceipeRoutingModule } from './add-receipe-routing.module';
import { AddReceipeComponent } from './add-receipe.component';

@NgModule({
  imports: [
    CommonModule,
    AddReceipeRoutingModule,
    HttpClientModule,
    DropDownsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSlideToggleModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  declarations: [AddReceipeComponent],
  exports: [
    DropDownsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSlideToggleModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ]
})
export class AddReceipeModule { }
