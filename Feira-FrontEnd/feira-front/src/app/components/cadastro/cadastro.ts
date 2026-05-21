import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css'
})
export class CadastroComponent {

  nome = '';
  email = '';
  senha = '';
  confirmarSenha = '';
  mensagem = '';

  mostrarSenha = false;
  mostrarConfirmarSenha = false;
  carregando = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cadastrar(): void {

    if (this.carregando) {
      return;
    }

    if (
      this.nome.trim() === '' ||
      this.email.trim() === '' ||
      this.senha.trim() === '' ||
      this.confirmarSenha.trim() === ''
    ) {
      this.mensagem = 'Preencha todos os campos.';
      return;
    }

    if (this.nome.trim().length < 3) {
      this.mensagem = 'Nome deve ter pelo menos 3 caracteres.';
      return;
    }

    const emailValido =
      /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.email);

    if (!emailValido) {
      this.mensagem = 'E-mail inválido.';
      return;
    }

    if (this.senha.length < 8) {
      this.mensagem = 'A senha deve ter pelo menos 8 caracteres.';
      return;
    }

    if (this.senha !== this.confirmarSenha) {
      this.mensagem = 'As senhas não coincidem.';
      return;
    }

    this.carregando = true;
    this.mensagem = '';

    this.authService.cadastrar(
      this.nome,
      this.email,
      this.senha
    ).subscribe({

      next: () => {

        this.authService.login(
          this.email,
          this.senha
        )
          .pipe(
            finalize(() => {
              this.carregando = false;
            })
          )
          .subscribe({

            next: (resposta) => {

              this.authService.salvarLogin(
                resposta.token,
                resposta.nome
              );

              this.router.navigate(['/home']);
            },

            error: () => {
              this.mensagem = 'Usuário cadastrado, mas houve erro ao entrar.';
            }
          });
      },

      error: (erro) => {

        this.mensagem =
          erro.error?.erro ||
          'Erro ao cadastrar usuário.';

        this.carregando = false;
      }
    });
  }

  alternarSenha(): void {

    this.mostrarSenha = !this.mostrarSenha;
  }

  alternarConfirmarSenha(): void {

    this.mostrarConfirmarSenha = !this.mostrarConfirmarSenha;
  }
}