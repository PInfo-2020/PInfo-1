import { KeycloakService } from 'src/app/services/keycloak/keycloak.service';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()

export  class KeycloakAuthGuard implements CanActivate {

  protected authenticated: boolean;

  protected roles: string[];

  constructor(protected router: Router, public keycloak: KeycloakService) {}

  /**
   * CanActivate checks if the user is logged in and get the full list of roles (REALM + CLIENT)
   * of the logged user. This values are set to authenticated and roles params.
   *
   * @param route
   * @param state
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {
    return new Promise(async (resolve, reject) => {
      try {
        this.authenticated = await this.keycloak.isLoggedIn();
        this.roles = await this.keycloak.getUserRoles();
        // console.log('roles :', this.roles);

        const result = await this.isAccessAllowed(route, state);
        resolve(result);
      } catch (error) {
        reject('An error happened during access validation. Details:' + error);
      }
    });
  }

  /**
   * Create your own customized authorization flow in this method. From here you already known
   * if the user is authenticated (this.authenticated) and the user roles (this.roles).
   *
   * Return a UrlTree if the user should be redirected to another route.
   *
   * @param route
   * @param state
   */
  isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    return new Promise(async (resolve, reject) => {
      if (!this.authenticated) {
        this.keycloak.login();
        return;
      }
      console.log('role restriction given at app-routing.module for this route', route.data.roles);
      console.log('User roles coming after login from keycloak :', this.roles);
      const requiredRoles = route.data.roles;
      let granted = false;
      if (!requiredRoles || requiredRoles.length === 0) {
        granted = true;
      } else {
        for (const requiredRole of requiredRoles) {
          // console.log('Required role :', requiredRole);
          if (this.roles.indexOf(requiredRole) > -1) {
            granted = true;
            break;
          }
        }
      }

      if (granted === false) {
        // console.log('jai foiré');
        this.router.navigate(['/']);
      }
      resolve(granted);

    });
  }
}

