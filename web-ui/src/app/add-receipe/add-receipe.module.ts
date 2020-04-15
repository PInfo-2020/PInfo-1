import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AddReceipeRoutingModule } from './add-receipe-routing.module';
import { AddReceipeComponent } from './add-receipe.component';

@NgModule({
  imports: [
    CommonModule,
    AddReceipeRoutingModule
  ],
  declarations: [AddReceipeComponent]
})
export class AddReceipeModule { }
