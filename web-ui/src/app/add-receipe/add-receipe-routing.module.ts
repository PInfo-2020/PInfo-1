import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AddReceipeComponent } from './add-receipe.component';
import { UploadPhotoComponent } from './upload-photo/upload-photo.component';
import { NameInputComponent } from './name-input/name-input.component';
import { TimeInputComponent } from './time-input/time-input.component';
import { DifficultyInputComponent } from './difficulty-input/difficulty-input.component';
import { TagsInputComponent } from './tags-input/tags-input.component';
import { IngredientsInputComponent } from './ingredients-input/ingredients-input.component';




const routes: Routes = [
  { path: 'add-receipe', component: AddReceipeComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    DropDownsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
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
    MatSlideToggleModule,
    ReactiveFormsModule,
    FormsModule,
    UploadPhotoComponent,
    NameInputComponent,
    TimeInputComponent,
    DifficultyInputComponent,
    TagsInputComponent,
    DropDownsModule,
    IngredientsInputComponent,
  ],
  declarations: [
    UploadPhotoComponent,
    NameInputComponent,
    TimeInputComponent,
    DifficultyInputComponent,
    TagsInputComponent,
    IngredientsInputComponent,
  ]
})
export class AddReceipeRoutingModule { }
