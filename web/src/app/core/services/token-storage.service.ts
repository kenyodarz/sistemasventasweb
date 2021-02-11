import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token'
const USER_KEY = 'auth-user'

@Injectable({
  providedIn: 'root',
})
export class TokenStorageService {
  constructor() {}

  signOut() {
    window.sessionStorage.clear();
  }

  guardarToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  obtenerToken(): string {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  guardarUsuario(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  obtenerUsuario() {
    return JSON.parse(sessionStorage.getItem(USER_KEY));
  }
}
