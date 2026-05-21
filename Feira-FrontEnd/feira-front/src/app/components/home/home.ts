import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

import { Categoria } from '../../models/categoria';
import { Feirante } from '../../models/feirante';

import { CategoriaService } from '../../services/categoria.service';
import { FeiranteService } from '../../services/feirante.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent implements OnInit {

  categorias: Categoria[] = [];
  feirantes: Feirante[] = [];

  carregandoCategorias = false;
  carregandoFeirantes = false;

  mensagem = '';

  constructor(
    private categoriaService: CategoriaService,
    private feiranteService: FeiranteService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.carregarDados();
  }

  carregarDados(): void {
    this.listarCategorias();
    this.listarFeirantes();
  }

  listarCategorias(): void {
    this.carregandoCategorias = true;

    this.categoriaService.listar()
      .pipe(
        finalize(() => {
          this.carregandoCategorias = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (dados) => {
          this.categorias = dados;
        },
        error: () => {
          this.mensagem = 'Erro ao carregar categorias.';
        }
      });
  }

  listarFeirantes(): void {
    this.carregandoFeirantes = true;

    this.feiranteService.listar()
      .pipe(
        finalize(() => {
          this.carregandoFeirantes = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (dados) => {
          this.feirantes = dados;
        },
        error: () => {
          this.mensagem = 'Erro ao carregar feirantes.';
        }
      });
  }

  totalCategorias(): number {
    return this.categorias.length;
  }

  totalFeirantes(): number {
    return this.feirantes.length;
  }

  totalAtivos(): number {
    return this.feirantes.filter(feirante => feirante.ativo).length;
  }

  totalInativos(): number {
    return this.feirantes.filter(feirante => !feirante.ativo).length;
  }

  carregando(): boolean {
    return this.carregandoCategorias || this.carregandoFeirantes;
  }
}