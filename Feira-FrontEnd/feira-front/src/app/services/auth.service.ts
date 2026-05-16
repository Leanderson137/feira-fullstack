import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { LoginResponse } from '../models/login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/auth';

  private tokenKey = 'token';

  private nomeKey = 'nome';

  constructor(private http: HttpClient) {}

  login(
    email: string,
    senha: string
  ): Observable<LoginResponse> {

    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
      {
        email,
        senha
      }
    );
  }

  cadastrar(
    nome: string,
    email: string,
    senha: string
  ) {

    return this.http.post<void>(
      `${this.apiUrl}/cadastrar`,
      {
        nome,
        email,
        senha
      }
    );
  }

  salvarLogin(
    token: string,
    nome: string
  ): void {

    localStorage.setItem(
      this.tokenKey,
      token
    );

    localStorage.setItem(
      this.nomeKey,
      nome
    );
  }

  pegarToken(): string | null {

    return localStorage.getItem(
      this.tokenKey
    );
  }

  pegarNomeUsuario(): string {

    return localStorage.getItem(
      this.nomeKey
    ) || '';
  }

  logout(): void {

    localStorage.removeItem(
      this.tokenKey
    );

    localStorage.removeItem(
      this.nomeKey
    );
  }

  estaLogado(): boolean {

    return this.pegarToken() !== null;
  }
}