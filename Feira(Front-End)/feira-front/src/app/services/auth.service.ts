import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private logado = false;

  login(): void {
    this.logado = true;
  }

  logout(): void {
    this.logado = false;
  }

  estaLogado(): boolean {
    return this.logado;
  }
}