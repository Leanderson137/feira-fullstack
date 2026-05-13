import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

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

  indiceEdicao: number | null = null;
  idEmEdicao: number | null = null;

  categorias: Categoria[] = [];

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.listarCategorias();
  }

  listarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (dados) => {
        this.categorias = dados;
      },
      error: () => {
        this.mensagem = 'Erro ao listar categorias.';
      }
    });
  }

  cadastrar(): void {

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

    if (this.idEmEdicao === null) {

      this.categoriaService.cadastrar(categoriaRequest).subscribe({
        next: () => {
          this.limparFormulario();
          this.indiceEdicao = null;
          this.idEmEdicao = null;
          this.mensagem = 'Categoria cadastrada com sucesso.';
          this.listarCategorias();
        },
        error: (erro) => {
          this.mensagem = erro.error?.erro || 'Já existe uma categoria com esse nome.';
        }
      });

    } else {

      this.categoriaService.atualizar(this.idEmEdicao, categoriaRequest).subscribe({
        next: () => {
          this.limparFormulario();
          this.indiceEdicao = null;
          this.idEmEdicao = null;
          this.mensagem = 'Categoria atualizada com sucesso.';
          this.listarCategorias();
        },
        error: (erro) => {
          this.mensagem = erro.error?.erro || 'Erro ao atualizar categoria.';
        }
      });
    }
  }

  editar(index: number): void {

    const categoria = this.categorias[index];

    this.nome = categoria.nome;
    this.descricao = categoria.descricao;

    this.indiceEdicao = index;
    this.idEmEdicao = categoria.id;

    this.mensagem = 'Editando categoria.';
  }

  excluir(index: number): void {

    const confirmar = confirm('Tem certeza que deseja excluir esta categoria?');

    if (!confirmar) {
      return;
    }

    const categoria = this.categorias[index];

    this.categoriaService.excluir(categoria.id).subscribe({
      next: () => {
        this.mensagem = 'Categoria excluída com sucesso.';
        this.listarCategorias();

        if (this.indiceEdicao === index) {
          this.cancelarEdicao();
        }
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

    this.indiceEdicao = null;
    this.idEmEdicao = null;

    this.limparFormulario();

    this.mensagem = 'Edição cancelada.';
  }

  limparFormulario(): void {

    this.nome = '';
    this.descricao = '';
  }
}