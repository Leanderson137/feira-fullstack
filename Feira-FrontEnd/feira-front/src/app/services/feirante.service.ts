import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Feirante } from '../models/feirante';
import { FeiranteRequest } from '../models/feirante-request';

@Injectable({
  providedIn: 'root'
})
export class FeiranteService {

  private apiUrl = 'https://feira-fullstack.onrender.com/feirante';

  constructor(private http: HttpClient) {}

  listar(): Observable<Feirante[]> {
    return this.http.get<Feirante[]>(this.apiUrl);
  }

  cadastrar(feirante: FeiranteRequest): Observable<Feirante> {
    return this.http.post<Feirante>(this.apiUrl, feirante);
  }

  atualizar(id: number, feirante: FeiranteRequest): Observable<Feirante> {
    return this.http.put<Feirante>(`${this.apiUrl}/${id}`, feirante);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  buscarPorId(id: number): Observable<Feirante> {
    return this.http.get<Feirante>(`${this.apiUrl}/${id}`);
  }
}