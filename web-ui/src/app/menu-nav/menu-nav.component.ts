import { Component, OnInit, AfterViewInit } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { KeycloakService } from 'keycloak-angular';
import { KeycloakProfile } from 'keycloak-js';



@Component({
  selector: 'app-menu-nav',
  templateUrl: './menu-nav.component.html',
  styleUrls: ['./menu-nav.component.css']
})

export class MenuNavComponent implements OnInit {
  userDetails: KeycloakProfile;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
      .pipe(
        map(result => result.matches),
        shareReplay()
      );

  constructor(private breakpointObserver: BreakpointObserver, public keycloak: KeycloakService) {
  }

  async ngOnInit() {
    if (await this.keycloak.isLoggedIn()) {
      console.log("prout")
      this.userDetails = await this.keycloak.loadUserProfile();
    }
  }
  async profil() {
    if (await this.keycloak.isLoggedIn()) {
      this.keycloak.getKeycloakInstance().accountManagement();
    }
  }
  async login(){
    await this.keycloak.login({ redirectUri: document.baseURI+"my-fridge"});
  }
  async logout() {
    await this.keycloak.logout(document.baseURI);
  }
  async isLoggedIn(): Promise<boolean> {
    return await this.keycloak.isLoggedIn();
  }



}
