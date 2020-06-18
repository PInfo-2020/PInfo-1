import { DashboardComponent } from './dashboard/dashboard.component';
import { AddReceipeComponent } from './add-receipe/add-receipe.component';
import { MyFridgeComponent } from './my-fridge/my-fridge.component';
import { CanAuthenticationGuard } from './services/keycloak/keycloak-auth-gard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewRecipeComponent } from './view-recipe/view-recipe.component';
import { SearchRecipeComponent } from './search-recipe/search-recipe.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
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
  },
  {
    path: 'view-recipe/:id',
    component: ViewRecipeComponent
  },
  {
    path: 'search-recipe',
    component: SearchRecipeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [CanAuthenticationGuard]
})
export class AppRoutingModule { }

