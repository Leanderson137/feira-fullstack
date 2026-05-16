import { Categoria } from './categoria';

export interface Feirante {
  id: number;
  nome: string;
  cpf: string;
  ativo: boolean;
  categoria: Categoria;
}