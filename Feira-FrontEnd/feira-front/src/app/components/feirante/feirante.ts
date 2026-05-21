import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs';

import { Feirante } from '../../models/feirante';
import { FeiranteRequest } from '../../models/feirante-request';
import { Categoria } from '../../models/categoria';

import { FeiranteService } from '../../services/feirante.service';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-feirante',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './feirante.html',
  styleUrl: './feirante.css'
})
export class FeiranteComponent implements OnInit {

  nome = '';
  cpf = '';
  ativo = true;
  categoriaId: number | null = null;
  mensagem = '';

  idEmEdicao: number | null = null;

  feirantes: Feirante[] = [];
  categorias: Categoria[] = [];

  carregandoFeirantes = false;
  carregandoCategorias = false;
  salvando = false;
  excluindoId: number | null = null;

  constructor(
    private feiranteService: FeiranteService,
    private categoriaService: CategoriaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.listarFeirantes();
    this.listarCategorias();
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
          this.mensagem = 'Erro ao listar feirantes.';
        }
      });
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

  permitirSomenteNumeros(event: KeyboardEvent): void {
    const teclasPermitidas = [
      'Backspace',
      'Delete',
      'Tab',
      'ArrowLeft',
      'ArrowRight'
    ];

    if (teclasPermitidas.includes(event.key)) {
      return;
    }

    if (!/^\d$/.test(event.key)) {
      event.preventDefault();
    }
  }

  formatarCpf(): void {
    let numeros = this.cpf.replace(/\D/g, '');

    if (numeros.length > 11) {
      numeros = numeros.substring(0, 11);
    }

    if (numeros.length <= 3) {
      this.cpf = numeros;
    } else if (numeros.length <= 6) {
      this.cpf = `${numeros.substring(0, 3)}.${numeros.substring(3)}`;
    } else if (numeros.length <= 9) {
      this.cpf = `${numeros.substring(0, 3)}.${numeros.substring(3, 6)}.${numeros.substring(6)}`;
    } else {
      this.cpf = `${numeros.substring(0, 3)}.${numeros.substring(3, 6)}.${numeros.substring(6, 9)}-${numeros.substring(9)}`;
    }
  }

  salvar(): void {
    if (this.salvando) {
      return;
    }

    const cpfNumeros = this.cpf.replace(/\D/g, '');

    if (
      this.nome.trim() === '' ||
      cpfNumeros.trim() === '' ||
      this.categoriaId === null
    ) {
      this.mensagem = 'Preencha todos os campos obrigatórios.';
      return;
    }

    if (this.nome.trim().length < 3) {
      this.mensagem = 'Nome deve ter pelo menos 3 caracteres.';
      return;
    }

    const cpfValido = /^\d{11}$/.test(cpfNumeros);

    if (!cpfValido) {
      this.mensagem = 'CPF inválido.';
      return;
    }

    const feiranteRequest: FeiranteRequest = {
      nome: this.nome.trim(),
      cpf: cpfNumeros,
      ativo: this.ativo,
      categoriaId: this.categoriaId
    };

    this.salvando = true;
    this.mensagem = '';

    if (this.idEmEdicao === null) {

      this.feiranteService.cadastrar(feiranteRequest)
        .pipe(
          finalize(() => {
            this.salvando = false;
            this.cdr.detectChanges();
          })
        )
        .subscribe({
          next: () => {
            this.mensagem = 'Feirante cadastrado com sucesso.';
            this.limparFormulario();
            this.listarFeirantes();
          },
          error: (erro) => {
            this.mensagem =
              erro.error?.erro || 'Erro ao cadastrar feirante.';
          }
        });

    } else {

      this.feiranteService.atualizar(this.idEmEdicao, feiranteRequest)
        .pipe(
          finalize(() => {
            this.salvando = false;
            this.cdr.detectChanges();
          })
        )
        .subscribe({
          next: () => {
            this.mensagem = 'Feirante atualizado com sucesso.';
            this.limparFormulario();
            this.listarFeirantes();
          },
          error: (erro) => {
            this.mensagem =
              erro.error?.erro || 'Erro ao atualizar feirante.';
          }
        });
    }
  }

  editar(feirante: Feirante): void {
    if (this.salvando || this.excluindoId !== null) {
      return;
    }

    this.nome = feirante.nome;
    this.cpf = feirante.cpf;
    this.formatarCpf();
    this.ativo = feirante.ativo;
    this.categoriaId = feirante.categoria.id;
    this.idEmEdicao = feirante.id;
    this.mensagem = 'Editando feirante.';
  }

  excluir(feirante: Feirante): void {
    const confirmar = confirm('Tem certeza que deseja excluir este feirante?');

    if (!confirmar || this.excluindoId !== null) {
      return;
    }

    this.excluindoId = feirante.id;
    this.mensagem = '';

    this.feiranteService.excluir(feirante.id)
      .pipe(
        finalize(() => {
          this.excluindoId = null;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: () => {
          this.mensagem = 'Feirante excluído com sucesso.';
          this.listarFeirantes();

          if (this.idEmEdicao === feirante.id) {
            this.cancelarEdicao();
          }
        },
        error: () => {
          this.mensagem = 'Erro ao excluir feirante.';
        }
      });
  }

  cancelarEdicao(): void {
    this.idEmEdicao = null;
    this.limparFormulario();
    this.mensagem = 'Edição cancelada.';
  }

  limparFormulario(): void {
    this.nome = '';
    this.cpf = '';
    this.ativo = true;
    this.categoriaId = null;
    this.idEmEdicao = null;
  }

  textoBotaoSalvar(): string {
    if (this.salvando) {
      return this.idEmEdicao === null ? 'Cadastrando...' : 'Atualizando...';
    }

    return this.idEmEdicao === null ? 'Salvar Feirante' : 'Atualizar Feirante';
  }
}