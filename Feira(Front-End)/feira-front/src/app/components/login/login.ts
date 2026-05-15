import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  email = '';
  senha = '';
  mensagem = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  entrar(): void {

    if (this.email.trim() === '' || this.senha.trim() === '') {
     this.mensagem = 'Informe e-mail e senha.';
     return;
    }

    const emailValido = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email);

     if (!emailValido) {
     this.mensagem = 'E-mail inválido.';
       return;
     }

     if (this.senha.length < 8) {
      this.mensagem = 'A senha deve ter pelo menos 8 caracteres.';
      return;
    }

    this.authService.login();

     this.router.navigate(['/home']);
  }
}