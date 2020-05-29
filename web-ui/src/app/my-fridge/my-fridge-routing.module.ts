import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MyFridgeComponent } from './my-fridge.component';
import { IngredientsInputComponent } from './ingredients-input/ingredients-input.component';
import { FridgeContentComponent } from './fridge-content/fridge-content.component';

const routes: Routes = [
  { path: 'my-fridge', component: MyFridgeComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    DropDownsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSlideToggleModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ],
  exports: [
    RouterModule,
    CommonModule,
    DropDownsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSlideToggleModule,
    ReactiveFormsModule,
    FormsModule,
    DropDownsModule,
    IngredientsInputComponent,
    FridgeContentComponent,
  ],
  declarations: [
    IngredientsInputComponent,
    FridgeContentComponent,
  ]
})

export class MyFridgeRoutingModule { }
