import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, RouterOutlet, CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {

  constructor(
    public router: Router,
    private authService: AuthService
  ) {}

  estaNaTelaLogin(): boolean {
    return this.router.url === '/login';
  }

  sair(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}