import { Injectable } from '@angular/core';
import * as Keycloak from 'keycloak-js';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})

// let Keycloak: any;

export class KeycloakService {

  static auth: any = {};

  public init(): Promise<any> {

  // const keycloakAuth: Keycloak.KeycloakInstance = Keycloak(environment.keycloak);
  const keycloakAuth: Keycloak.KeycloakInstance = Keycloak(
    {
      url: environment.keycloak.url,
      realm: environment.keycloak.realm,
      clientId: environment.keycloak.clientId,
    });

  KeycloakService.auth.loggedIn = false;
  return new Promise((resolve, reject) => {
      keycloakAuth.init({ onLoad: 'check-sso', checkLoginIframe: false })
        .then(() => {
          KeycloakService.auth.loggedIn = false;
          KeycloakService.auth.authz = keycloakAuth;
          console.log(KeycloakService.auth.authz.tokenParsed);
          resolve();
        })
        .catch(() => {
          reject();
        });
    });
  }

  constructor() { }

  login(): void {
    KeycloakService.auth.authz.login().success(
      () => {
        KeycloakService.auth.loggedIn = true;
      }
    );
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.authz.token) {
        KeycloakService.auth.authz
          .updateToken(5)
          .success(() => {
            resolve(KeycloakService.auth.authz.token as string); // <string>KeycloakService.auth.authz.token
          })
          .error(() => {
            reject('Failed to refresh token');
          });
      } else {
        reject('Not logged in');
      }
    });
  }

  isLoggedIn(): boolean {
    return KeycloakService.auth.authz.authenticated;
  }

  getFullName(): string {
    if (this.isLoggedIn()) {
        return KeycloakService.auth.authz.tokenParsed.name;
    } else { return 'guest'; }
  }

  getUsername(): string {
    if (this.isLoggedIn()) {
        return KeycloakService.auth.authz.tokenParsed.preferred_username;
    } else { return 'guest'; }
  }

  getKeycloakAuth() {
    return KeycloakService.auth.authz;
  }

  logout(): void {
    KeycloakService.auth.authz.logout({redirectUri : document.baseURI}).success(() => {
      KeycloakService.auth.loggedIn = false;
      KeycloakService.auth.authz = null;
    });
  }

}
