import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MyComponentTestComponent } from './my-component-test/my-component-test.component';
import { NotMyComponentComponent } from './not-my-component/not-my-component.component';

@NgModule({
   declarations: [
      AppComponent,
      MyComponentTestComponent,
      NotMyComponentComponent
   ],
   imports: [
      BrowserModule,
      AppRoutingModule
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
