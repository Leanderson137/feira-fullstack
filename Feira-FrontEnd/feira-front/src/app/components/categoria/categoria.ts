import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

import { Categoria } from '../../models/categoria';
import { CategoriaRequest } from '../../models/categoria-request';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-categoria',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './categoria.html',
  styleUrl: './categoria.css'
})
export class CategoriaComponent implements OnInit {

  nome = '';
  descricao = '';
  mensagem = '';

  idEmEdicao: number | null = null;

  categorias: Categoria[] = [];

  carregandoLista = false;
  salvando = false;
  excluindoId: number | null = null;

  constructor(
    private categoriaService: CategoriaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.listarCategorias();
  }

  listarCategorias(): void {
    this.carregandoLista = true;

    this.categoriaService.listar()
      .pipe(
        finalize(() => {
          this.carregandoLista = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (dados) => {
          this.categorias = dados;
        },
        error: () => {
          this.mensagem = 'Erro ao listar categorias.';
        }
      });
  }

  salvar(): void {
    if (this.salvando) {
      return;
    }

    if (this.nome.trim() === '' || this.descricao.trim() === '') {
      this.mensagem = 'Preencha todos os campos obrigatórios.';
      return;
    }

    if (this.nome.trim().length < 3) {
      this.mensagem = 'Nome deve ter pelo menos 3 caracteres.';
      return;
    }

    const categoriaRequest: CategoriaRequest = {
      nome: this.nome.trim(),
      descricao: this.descricao.trim()
    };

    this.salvando = true;
    this.mensagem = '';

    if (this.idEmEdicao === null) {

      this.categoriaService.cadastrar(categoriaRequest)
        .pipe(
          finalize(() => {
            this.salvando = false;
            this.cdr.detectChanges();
          })
        )
        .subscribe({
          next: () => {
            this.mensagem = 'Categoria cadastrada com sucesso.';
            this.limparFormulario();
            this.listarCategorias();
          },
          error: (erro) => {
            this.mensagem =
              erro.error?.erro || 'Erro ao cadastrar categoria.';
          }
        });

    } else {

      this.categoriaService.atualizar(this.idEmEdicao, categoriaRequest)
        .pipe(
          finalize(() => {
            this.salvando = false;
            this.cdr.detectChanges();
          })
        )
        .subscribe({
          next: () => {
            this.mensagem = 'Categoria atualizada com sucesso.';
            this.limparFormulario();
            this.listarCategorias();
          },
          error: (erro) => {
            this.mensagem =
              erro.error?.erro || 'Erro ao atualizar categoria.';
          }
        });
    }
  }

  editar(categoria: Categoria): void {
    if (this.salvando || this.excluindoId !== null) {
      return;
    }

    this.idEmEdicao = categoria.id;
    this.nome = categoria.nome;
    this.descricao = categoria.descricao;
    this.mensagem = 'Editando categoria.';
  }

  excluir(categoria: Categoria): void {
    const confirmar = confirm('Tem certeza que deseja excluir esta categoria?');

    if (!confirmar || this.excluindoId !== null) {
      return;
    }

    this.excluindoId = categoria.id;
    this.mensagem = '';

    this.categoriaService.excluir(categoria.id)
      .pipe(
        finalize(() => {
          this.excluindoId = null;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: () => {
          this.mensagem = 'Categoria excluída com sucesso.';
          this.listarCategorias();
        },
        error: (erro) => {
          this.mensagem =
            erro.error?.erro ||
            erro.error?.message ||
            'Não foi possível excluir a categoria.';
        }
      });
  }

  cancelarEdicao(): void {
    this.limparFormulario();
    this.mensagem = 'Edição cancelada.';
  }

  limparFormulario(): void {
    this.nome = '';
    this.descricao = '';
    this.idEmEdicao = null;
  }

  textoBotaoSalvar(): string {
    if (this.salvando) {
      return this.idEmEdicao === null ? 'Cadastrando...' : 'Atualizando...';
    }

    return this.idEmEdicao === null ? 'Cadastrar' : 'Atualizar';
  }
}