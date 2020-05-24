import { KeycloakInterceptorService } from './../services/keycloak/keycloak.interceptor.service';
import { Component } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { KeycloakService } from './../services/keycloak/keycloak.service';

@Component({
  selector: 'app-menu-nav',
  templateUrl: './menu-nav.component.html',
  styleUrls: ['./menu-nav.component.css']
})

export class MenuNavComponent {


  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private breakpointObserver: BreakpointObserver, public keycloak: KeycloakService) {}

  onProfil() {
    console.log('fdp');
    if (this.keycloak.isLoggedIn() === false) {
      console.log('je suis dedans , kyaaaaaa');
      this.keycloak.login();
    }
  }

  logOut() {
    if (this.keycloak.isLoggedIn() === true){
      console.log('je sors , yemeteeeee');
      this.keycloak.logout();
    }
  }

}
