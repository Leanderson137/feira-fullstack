# Sistema de Gerenciamento de Feirantes e Categorias

## Descrição do Projeto

Este projeto consiste em uma aplicação web full stack desenvolvida com Spring Boot no backend e Angular no frontend, com o objetivo de realizar o gerenciamento de feirantes e categorias.

O sistema permite cadastrar, listar, editar e remover categorias e feirantes, além de aplicar validações e regras de negócio para garantir a integridade dos dados.

O projeto foi desenvolvido como atividade acadêmica utilizando metodologia Scrum e Kanban com GitHub Projects.

---

# Tecnologias Utilizadas

## Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Maven
* H2 Database
* Bean Validation

## Frontend

* Angular
* TypeScript
* HTML
* CSS
* RxJS

## Ferramentas

* Git
* GitHub
* Postman
* VS Code
* IntelliJ IDEA

---

# Funcionalidades

## Categorias

* Cadastrar categoria
* Listar categorias
* Editar categoria
* Excluir categoria
* Bloquear exclusão de categoria em uso
* Validação de campos obrigatórios
* Tratamento de categorias duplicadas

## Feirantes

* Cadastrar feirante
* Listar feirantes
* Editar feirante
* Excluir feirante
* Relacionamento com categoria
* Validação de CPF
* Tratamento de CPF duplicado

---

# Arquitetura do Projeto

O backend foi organizado em camadas seguindo boas práticas de desenvolvimento:

* Controller
* Service
* Repository
* DTO
* Exception
* Entity

O frontend Angular consome a API REST desenvolvida no backend utilizando HttpClient e RxJS.

---

# Estrutura do Projeto

```txt
backend/
frontend/
README.md
```

---

# Como Executar o Backend

## Pré-requisitos

* Java 17+
* Maven

## Passos

```bash
cd backend/feira-back
mvn spring-boot:run
```

O backend ficará disponível em:

```txt
http://localhost:8080
```

---

# Como Executar o Frontend

## Pré-requisitos

* Node.js LTS
* Angular CLI

## Passos

```bash
cd frontend/feira-front
npm install
ng serve
```

O frontend ficará disponível em:

```txt
http://localhost:4200
```

---

# Principais Endpoints

## Categoria

### Listar categorias

```http
GET /categoria
```

### Cadastrar categoria

```http
POST /categoria
```

### Atualizar categoria

```http
PUT /categoria/{id}
```

### Remover categoria

```http
DELETE /categoria/{id}
```

---

## Feirante

### Listar feirantes

```http
GET /feirante
```

### Cadastrar feirante

```http
POST /feirante
```

### Atualizar feirante

```http
PUT /feirante/{id}
```

### Remover feirante

```http
DELETE /feirante/{id}
```

---

# Validações Implementadas

* Campos obrigatórios
* Nome mínimo de 3 caracteres
* CPF inválido
* CPF duplicado
* Categoria duplicada
* Bloqueio de exclusão de categoria em uso

---

# Tratamento de Erros

O projeto utiliza:

* RegraNegocioException
* GlobalExceptionHandler
* Bean Validation
* Respostas HTTP padronizadas

---

# Padrões de Projeto Utilizados

* MVC
* Repository Pattern
* Service Layer
* DTO
* Observer (RxJS no Angular)

---

# Melhorias Futuras

* Implementação de autenticação
* Utilização de banco MySQL
* Melhorias visuais com framework CSS
* Deploy em nuvem
* Paginação de dados
* Busca e filtros

---

# Autores

Projeto desenvolvido para disciplina acadêmica utilizando Spring Boot e Angular.
