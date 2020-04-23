import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddReceipeComponent } from './add-receipe.component';
import { UploadPhotoComponent } from './upload-photo/upload-photo.component';
import { NameInputComponent } from './name-input/name-input.component';

const routes: Routes = [
  { path: 'add-receipe', component: AddReceipeComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [
    RouterModule,
    UploadPhotoComponent,
    NameInputComponent
  ],
  declarations: [
    UploadPhotoComponent,
    NameInputComponent
  ]
})
export class AddReceipeRoutingModule { }
