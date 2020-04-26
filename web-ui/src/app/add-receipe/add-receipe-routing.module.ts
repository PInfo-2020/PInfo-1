import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddReceipeComponent } from './add-receipe.component';
import { UploadPhotoComponent } from './upload-photo/upload-photo.component';
import { NameInputComponent } from './name-input/name-input.component';
import { TimeInputComponent } from './time-input/time-input.component';
import { DifficultyInputComponent } from './difficulty-input/difficulty-input.component';
import { TagsInputComponent } from './tags-input/tags-input.component';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';


const routes: Routes = [
  { path: 'add-receipe', component: AddReceipeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes), DropDownsModule],
  exports: [
    RouterModule,
    UploadPhotoComponent,
    NameInputComponent,
    TimeInputComponent,
    DifficultyInputComponent,
    TagsInputComponent,
    DropDownsModule,
  ],
  declarations: [
    UploadPhotoComponent,
    NameInputComponent,
    TimeInputComponent,
    DifficultyInputComponent,
    TagsInputComponent
  ]
})
export class AddReceipeRoutingModule { }
