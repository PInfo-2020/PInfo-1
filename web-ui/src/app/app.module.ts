import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AppInitService } from './app.init';
import { KeycloakService, KeycloakAngularModule, KeycloakBearerInterceptor } from 'keycloak-angular';
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
import { environment } from 'src/environments/environment';
import { KeycloakInterceptorService } from './services/keycloak/keycloak.interceptor.service';
import { ViewRecipeModule } from './view-recipe/view-recipe.module';



declare var window: any;


export function init_config(appLoadService: AppInitService, keycloak: KeycloakService):() => Promise<any> {
  return (): Promise<any> => appLoadService.init().then( () => {
     return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
           config: {
              url: environment.keycloak.url,
              realm:  environment.keycloak.realm,
              clientId: environment.keycloak.clientId
            },
            initOptions: {
              onLoad: 'check-sso',
              checkLoginIframe: false
            },
            enableBearerInterceptor: false,
          });
        resolve();
      } catch (error) {
        reject(error);
      }
    });
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
      DropDownsModule,
      KeycloakAngularModule,
      HttpClientModule,
      ViewRecipeModule
   ],
   providers: [

    AppInitService,
    {
      provide: APP_INITIALIZER,
      useFactory: init_config,
      deps: [AppInitService, KeycloakService],
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakInterceptorService,
      multi: true,
    }
   ],
   bootstrap: [
      AppComponent,
      MenuNavComponent
   ]
})
export class AppModule { }
