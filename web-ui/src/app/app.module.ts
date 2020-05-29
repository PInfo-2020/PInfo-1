import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { CommonModule } from '@angular/common';

import { KeycloakService } from './services/keycloak/keycloak.service';
import { KeycloakInterceptorService } from './services/keycloak/keycloak.interceptor.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppInitService } from './app.init';

import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MenuNavComponent } from './menu-nav/menu-nav.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { DashboardModule } from './dashboard/dashboard.module';
import { MyReceipesModule } from './my-receipes/my-receipes.module';
import { MyFridgeModule } from './my-fridge/my-fridge.module';
import { AddReceipeModule } from './add-receipe/add-receipe.module';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { InputsModule } from '@progress/kendo-angular-inputs';

declare var window: any;

export function init_config(appLoadService: AppInitService, keycloak: KeycloakService) {
  return () =>  appLoadService.init().then( () => {
     console.info(window.config);
     keycloak.init();
    },
   );
}

@NgModule({
   declarations: [
      AppComponent,
      MenuNavComponent,
   ],
   imports: [
      BrowserModule,
      RouterModule.forRoot([]),
      AppRoutingModule,
      CommonModule,
      BrowserAnimationsModule,
      LayoutModule,
      MatToolbarModule,
      MatButtonModule,
      MatSidenavModule,
      MatIconModule,
      MatListModule,
      DashboardModule,
      MyReceipesModule,
      MyFridgeModule,
      AddReceipeModule,
      InputsModule,
      DropDownsModule
   ],
   providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakInterceptorService,
      multi: true,
    },
    AppInitService,
    {
      provide: APP_INITIALIZER,
      useFactory: init_config,
      deps: [AppInitService, KeycloakService],
      multi: true,
    },
    KeycloakService
   ],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule { }
