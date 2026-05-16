import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Categoria } from '../models/categoria';
import { CategoriaRequest } from '../models/categoria-request';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private apiUrl = 'https://feira-fullstack.onrender.com/categoria';

  constructor(private http: HttpClient) {}

  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }

  cadastrar(categoria: CategoriaRequest): Observable<Categoria> {
    return this.http.post<Categoria>(this.apiUrl, categoria);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  atualizar(id: number, categoria: CategoriaRequest): Observable<Categoria> {
  return this.http.put<Categoria>(`${this.apiUrl}/${id}`, categoria);
  }
}