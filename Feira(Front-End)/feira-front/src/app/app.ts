import { Component } from '@angular/core';
import {
  Router,
  RouterLink,
  RouterLinkActive,
  RouterOutlet
} from '@angular/router';

import { CommonModule } from '@angular/common';

import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    RouterOutlet,
    CommonModule
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  sidebarMinimizada = false;

  constructor(
    public router: Router,
    private authService: AuthService
  ) {}

  estaEmTelaPublica(): boolean {

    return (
      this.router.url === '/login' ||
      this.router.url === '/cadastro'
    );
  }

  pegarNomeUsuario(): string {

    return localStorage.getItem('nome') || '';
  }

  alternarSidebar(): void {

    this.sidebarMinimizada =
      !this.sidebarMinimizada;
  }

  sair(): void {

    this.authService.logout();

    this.router.navigate(['/login']);
  }
}