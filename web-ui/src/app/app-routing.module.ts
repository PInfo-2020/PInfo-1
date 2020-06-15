import { AddReceipeComponent } from './add-receipe/add-receipe.component';
import { MyFridgeComponent } from './my-fridge/my-fridge.component';
// import { ViewRecipeComponent } from  './view-recipe/view-recipe.component';
import { CanAuthenticationGuard } from './services/keycloak/keycloak-auth-gard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'fridge',
    redirectTo: '/my-fridge',
    pathMatch: 'full'
  },
  {
    path: 'my-fridge',
    // loadChildren: () => import('./my-fridge/my-fridge.module').then(m => m.MyFridgeModule) ,
    component: MyFridgeComponent,
    canActivate: [CanAuthenticationGuard],
    data: { roles: ['User'] }
  },
  {
    path: 'add-receipe',
    // loadChildren: () => import('./my-fridge/my-fridge.module').then(m => m.MyFridgeModule) ,
    component: AddReceipeComponent,
    canActivate: [CanAuthenticationGuard],
    data: { roles: ['User'] }
  }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [CanAuthenticationGuard]
})
export class AppRoutingModule { }

