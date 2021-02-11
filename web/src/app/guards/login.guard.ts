import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from '../core/services/token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class LoginGuard implements CanActivate {
  constructor(private router: Router, private token: TokenStorageService) {}
  canActivate() {
    if (!!this.token.obtenerToken()) {
      return true;
    } else {
      this.router.navigate(['/login'])
      return true;
    }
  }
}
