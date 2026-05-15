//

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

  salvar(): void {
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
          this.mensagem = 'Categoria cadastrada com sucesso.';
          this.limparFormulario();
          this.listarCategorias();
        },
        error: (erro) => {
          this.mensagem = erro.error?.erro || 'Erro ao cadastrar categoria.';
        }
      });
    } else {
      this.categoriaService.atualizar(this.idEmEdicao, categoriaRequest).subscribe({
        next: () => {
          this.mensagem = 'Categoria atualizada com sucesso.';
          this.limparFormulario();
          this.listarCategorias();
        },
        error: (erro) => {
          this.mensagem = erro.error?.erro || 'Erro ao atualizar categoria.';
        }
      });
    }
  }

  editar(categoria: Categoria): void {
    this.idEmEdicao = categoria.id;
    this.nome = categoria.nome;
    this.descricao = categoria.descricao;
    this.mensagem = 'Editando categoria.';
  }

  excluir(categoria: Categoria): void {
    const confirmar = confirm('Tem certeza que deseja excluir esta categoria?');

    if (!confirmar) {
      return;
    }

    this.categoriaService.excluir(categoria.id).subscribe({
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
}
