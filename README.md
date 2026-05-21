# Sistema Feira - Full Stack

Sistema web full stack para gerenciamento de feirantes e categorias, desenvolvido com **Angular** no front-end e **Spring Boot** no back-end.

O projeto possui autenticação com JWT, proteção de rotas, CRUD completo, isolamento de dados por usuário, dashboard, documentação Swagger/OpenAPI, testes automatizados com JUnit, Mockito, MockMvc e @DataJpaTest, backend dockerizado e deploy em nuvem.

---

## Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Links do Projeto](#links-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Back-end](#back-end)
- [Front-end](#front-end)
- [Autenticação e Segurança](#autenticação-e-segurança)
- [Isolamento de Dados por Usuário](#isolamento-de-dados-por-usuário)
- [Banco de Dados](#banco-de-dados)
- [Ambientes Local e Produção](#ambientes-local-e-produção)
- [Dashboard](#dashboard)
- [Responsividade](#responsividade)
- [Feedback Visual](#feedback-visual)
- [Swagger/OpenAPI](#swaggeropenapi)
- [Docker](#docker)
- [Testes Automatizados](#testes-automatizados)
- [Deploy](#deploy)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Como Rodar Localmente](#como-rodar-localmente)
- [Comandos Úteis](#comandos-úteis)
- [Endpoints Principais](#endpoints-principais)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Regras de Negócio](#regras-de-negócio)
- [Tratamento de Erros](#tratamento-de-erros)
- [Diferenciais Técnicos](#diferenciais-técnicos)
- [Possíveis Melhorias Futuras](#possíveis-melhorias-futuras)
- [Status do Projeto](#status-do-projeto)
- [Autor](#autor)

---

## Sobre o Projeto

O **Sistema Feira** é uma aplicação web criada para gerenciar feirantes e categorias de uma feira.

A aplicação permite que usuários criem suas contas, façam login, cadastrem categorias, cadastrem feirantes e visualizem um painel com informações resumidas do sistema.

Um dos principais pontos do projeto é o suporte a múltiplos usuários. Cada conta possui seus próprios registros, ou seja, um usuário não consegue visualizar, editar ou excluir dados cadastrados por outro usuário.

O projeto foi desenvolvido com foco em:

- organização em camadas;
- boas práticas de API REST;
- autenticação com JWT;
- separação entre front-end e back-end;
- integração real entre Angular e Spring Boot;
- deploy em nuvem;
- testes automatizados;
- documentação da API;
- responsividade;
- experiência de uso com feedback visual.

---

## Links do Projeto

### Aplicação Online

Front-end publicado na Vercel:

```txt
https://feira-fullstack.vercel.app
```

### API Online

Back-end publicado no Render:

```txt
https://feira-fullstack.onrender.com
```

### Repositório

```txt
https://github.com/Leanderson137/feira-fullstack
```

---

## Funcionalidades

### Usuário e Autenticação

- Cadastro de usuário.
- Login de usuário.
- Autenticação com JWT.
- Proteção de rotas no Angular.
- Proteção de endpoints no Spring Security.
- Logout.
- Armazenamento do token no navegador.
- Envio automático do token nas requisições protegidas.

### Categorias

- Cadastro de categorias.
- Listagem de categorias.
- Busca de categoria por ID.
- Edição de categorias.
- Exclusão de categorias.
- Validação de nome e descrição.
- Bloqueio de categoria duplicada por usuário.
- Impedimento de exclusão de categoria em uso.

### Feirantes

- Cadastro de feirantes.
- Listagem de feirantes.
- Busca de feirante por ID.
- Edição de feirantes.
- Exclusão de feirantes.
- Associação de feirante a uma categoria.
- Validação de nome.
- Validação de CPF.
- Bloqueio de CPF duplicado por usuário.
- Controle de feirante ativo/inativo.

### Multiusuário

- Cada usuário possui seus próprios dados.
- Categorias são vinculadas ao usuário logado.
- Feirantes são vinculados ao usuário logado.
- Um usuário não consegue acessar registros de outro usuário.
- O back-end valida a posse do recurso antes de permitir alteração ou exclusão.

### Interface

- Tela de login.
- Tela de cadastro.
- Home com dashboard.
- Tela de categorias.
- Tela de feirantes.
- Menu lateral no desktop.
- Menu adaptado no mobile.
- Feedback visual de carregamento.
- Botões com estado de carregamento.
- Bloqueio contra clique duplo em ações.
- Mensagens de sucesso e erro.
- Layout responsivo.

### Documentação e Qualidade

- Swagger/OpenAPI para documentação da API.
- Testes automatizados com JUnit.
- Testes de service com Mockito.
- Testes de repository com @DataJpaTest.
- Testes de controller com MockMvc.
- Backend dockerizado.
- Deploy do front-end na Vercel.
- Deploy do back-end no Render.

---

## Tecnologias Utilizadas

### Front-end

- Angular
- TypeScript
- HTML5
- CSS3
- Angular Router
- Angular Forms
- HttpClient
- Guards
- Interceptors
- Environment files

### Back-end

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT
- Hibernate
- Maven
- H2 Database
- Swagger/OpenAPI
- JUnit
- Mockito
- MockMvc
- @DataJpaTest

### Banco de Dados

- H2 Database

### Deploy e Infraestrutura

- Git
- GitHub
- Docker
- Render
- Vercel

### Ferramentas de Desenvolvimento

- IntelliJ IDEA
- Visual Studio Code
- Postman
- Swagger UI
- DevTools do navegador

---

## Arquitetura do Projeto

O projeto está dividido em duas partes principais:

```txt
Feira-Final
├── Feira-BackEnd
│   └── feira-back
│
├── Feira-FrontEnd
│   └── feira-front
│
└── README.md
```

### Estrutura do Back-end

```txt
Feira-BackEnd
└── feira-back
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── leanderson
    │   │   │           └── feira
    │   │   │               ├── controller
    │   │   │               ├── dto
    │   │   │               ├── entity
    │   │   │               ├── exception
    │   │   │               ├── repository
    │   │   │               ├── security
    │   │   │               └── service
    │   │   └── resources
    │   │       └── application.properties
    │   │
    │   └── test
    │       └── java
    │           └── com
    │               └── leanderson
    │                   └── feira
    │                       ├── controller
    │                       ├── entity
    │                       ├── repository
    │                       └── service
    │
    ├── Dockerfile
    ├── pom.xml
    ├── mvnw
    └── mvnw.cmd
```

### Estrutura do Front-end

```txt
Feira-FrontEnd
└── feira-front
    ├── src
    │   ├── app
    │   │   ├── components
    │   │   ├── guards
    │   │   ├── interceptors
    │   │   ├── models
    │   │   └── services
    │   │
    │   ├── environments
    │   │   ├── environment.ts
    │   │   └── environment.prod.ts
    │   │
    │   ├── main.ts
    │   └── styles.css
    │
    ├── angular.json
    ├── package.json
    ├── package-lock.json
    └── vercel.json
```

---

## Back-end

O back-end foi construído com Spring Boot e segue uma arquitetura em camadas.

### Controller

Camada responsável por receber requisições HTTP e retornar respostas para o cliente.

Principais controllers:

- `AuthController`
- `CategoriaController`
- `FeiranteController`
- `UsuarioController`

### Service

Camada responsável pelas regras de negócio.

Principais services:

- `AuthService`
- `CategoriaService`
- `FeiranteService`
- `UsuarioService`

Essa camada concentra validações como:

- impedir categoria duplicada;
- impedir CPF duplicado;
- validar usuário logado;
- impedir acesso a dados de outro usuário;
- validar se uma categoria pertence ao usuário logado;
- impedir exclusão de categoria em uso.

### Repository

Camada responsável pela comunicação com o banco de dados usando Spring Data JPA.

Principais repositories:

- `UsuarioRepository`
- `CategoriaRepository`
- `FeiranteRepository`

### Entity

Camada que representa as tabelas do banco de dados.

Principais entidades:

- `Usuario`
- `Categoria`
- `Feirante`

### DTO

Os DTOs são usados para entrada e saída de dados da API.

Principais DTOs:

- `UsuarioRequest`
- `UsuarioResponse`
- `LoginRequest`
- `LoginResponse`
- `CategoriaRequest`
- `CategoriaResponse`
- `FeiranteRequest`
- `FeiranteResponse`

O uso de DTOs evita expor diretamente as entidades do sistema e permite controlar melhor os dados enviados e recebidos.

---

## Front-end

O front-end foi desenvolvido com Angular.

Principais responsabilidades:

- exibir telas da aplicação;
- consumir a API REST;
- armazenar o token JWT;
- enviar token nas requisições protegidas;
- proteger rotas;
- exibir mensagens de erro e sucesso;
- controlar estados de carregamento;
- adaptar layout para desktop e mobile.

### Telas principais

- Login
- Cadastro
- Home/Dashboard
- Categorias
- Feirantes

### Services

Os services são responsáveis por fazer comunicação com o back-end.

Principais services:

- `AuthService`
- `CategoriaService`
- `FeiranteService`

### Models

Os models representam os dados utilizados no front-end.

Exemplos:

- `Categoria`
- `CategoriaRequest`
- `Feirante`
- `FeiranteRequest`
- `LoginResponse`

### Guards

O projeto possui proteção de rotas para impedir acesso a telas internas sem autenticação.

### Interceptors

O interceptor adiciona automaticamente o token JWT no cabeçalho das requisições protegidas.

Exemplo de cabeçalho enviado:

```txt
Authorization: Bearer token-jwt
```

---

## Autenticação e Segurança

A autenticação é baseada em JWT.

Fluxo de autenticação:

```txt
1. O usuário realiza cadastro.
2. O usuário realiza login.
3. O back-end valida e-mail e senha.
4. O back-end gera um token JWT.
5. O front-end salva o token.
6. O front-end envia o token nas próximas requisições.
7. O back-end valida o token.
8. Se o token for válido, o usuário acessa os endpoints protegidos.
```

### Rotas públicas

```txt
POST /auth/cadastrar
POST /auth/login
```

### Rotas protegidas

```txt
GET /categoria
POST /categoria
PUT /categoria/{id}
DELETE /categoria/{id}

GET /feirante
POST /feirante
PUT /feirante/{id}
DELETE /feirante/{id}

GET /usuarios
DELETE /usuarios/{id}
```

### Spring Security

O Spring Security foi configurado para:

- desabilitar CSRF em API REST stateless;
- liberar rotas de autenticação;
- proteger demais endpoints;
- usar sessão stateless;
- validar JWT por filtro;
- configurar CORS para front-end local e produção.

---

## Isolamento de Dados por Usuário

O sistema foi ajustado para que cada usuário visualize apenas seus próprios dados.

Exemplo:

```txt
Usuário A
├── Categorias do Usuário A
└── Feirantes do Usuário A

Usuário B
├── Categorias do Usuário B
└── Feirantes do Usuário B
```

Isso impede que dados sejam compartilhados de forma global entre contas diferentes.

### Categoria vinculada ao usuário

```java
@ManyToOne
@JoinColumn(name = "usuario_id")
private Usuario usuario;
```

### Feirante vinculado ao usuário

```java
@ManyToOne
@JoinColumn(name = "usuario_id")
private Usuario usuario;
```

### Feirante vinculado à categoria

```java
@ManyToOne
@JoinColumn(name = "categoria_id")
private Categoria categoria;
```

### Exemplo de regra aplicada no service

Antes de editar ou excluir um registro, o sistema verifica se ele pertence ao usuário logado.

Caso não pertença, a operação é bloqueada.

---

## Banco de Dados

O projeto utiliza H2 Database.

No ambiente local, o banco roda no computador do desenvolvedor.

No ambiente online, o banco roda dentro do serviço do Render.

Observação: por utilizar ambiente gratuito e banco H2, os dados podem ser apagados dependendo da configuração ou reinicialização do serviço. Para um ambiente de produção real, o ideal seria migrar para PostgreSQL.

---

## Ambientes Local e Produção

O front-end foi configurado com ambientes separados.

### Ambiente local

Arquivo:

```txt
src/environments/environment.ts
```

Conteúdo:

```ts
export const environment = {
  apiUrl: 'http://localhost:8080'
};
```

### Ambiente de produção

Arquivo:

```txt
src/environments/environment.prod.ts
```

Conteúdo:

```ts
export const environment = {
  apiUrl: 'https://feira-fullstack.onrender.com'
};
```

### Comportamento

```txt
ng serve
→ usa http://localhost:8080

build de produção
→ usa https://feira-fullstack.onrender.com
```

Isso permite testar o sistema localmente com rapidez e manter a versão online conectada ao back-end publicado.

---

## Dashboard

A Home possui um dashboard com dados reais do usuário logado.

Informações exibidas:

- total de feirantes;
- total de categorias;
- feirantes ativos;
- feirantes inativos.

Esses dados são carregados a partir da API e respeitam o isolamento por usuário.

---

## Responsividade

O layout foi ajustado para funcionar em desktop e dispositivos móveis.

### Desktop

- menu lateral;
- conteúdo em colunas;
- cards distribuídos em grade;
- melhor aproveitamento horizontal.

### Mobile

- menu adaptado para topo;
- conteúdo em uma coluna;
- cards empilhados;
- formulários com largura adequada;
- redução de espaçamentos;
- melhor navegação em telas pequenas.

---

## Feedback Visual

O front-end possui melhorias de experiência do usuário, como:

- botão `Entrando...` no login;
- botão `Cadastrando...` no cadastro;
- botão `Cadastrando...` ou `Atualizando...` em formulários;
- botão `Excluindo...` em exclusões;
- mensagens de sucesso;
- mensagens de erro;
- bloqueio de clique duplo;
- estado de carregamento em listas.

Essas melhorias tornam a aplicação mais estável visualmente, principalmente em hospedagens gratuitas que podem ter delay.

---

## Swagger/OpenAPI

A API possui documentação com Swagger/OpenAPI.

Com o back-end rodando localmente, acesse:

```txt
http://localhost:8080/swagger-ui/index.html
```

No Swagger é possível testar endpoints como:

```txt
POST /auth/login
POST /auth/cadastrar
GET /categoria
POST /categoria
GET /feirante
POST /feirante
GET /usuarios
```

### Como testar endpoints protegidos no Swagger

```txt
1. Execute POST /auth/login.
2. Copie o token retornado.
3. Clique em Authorize.
4. Insira o token no formato: Bearer SEU_TOKEN.
5. Execute os endpoints protegidos.
```

---

## Docker

O back-end possui Dockerfile para build e execução da aplicação.

O Docker permite criar um ambiente padronizado para rodar o back-end, facilitando o deploy no Render.

Fluxo geral:

```txt
1. O Render clona o repositório.
2. O Dockerfile é executado.
3. O Maven gera o arquivo .jar.
4. A aplicação Spring Boot é iniciada dentro do container.
5. A API fica disponível publicamente.
```

---

## Testes Automatizados

O projeto possui testes automatizados cobrindo diferentes camadas da aplicação, utilizando **JUnit**, **Mockito**, **MockMvc** e **@DataJpaTest**.

A suíte de testes foi criada para validar desde regras internas das entidades até regras de negócio, persistência em repository e comportamento dos controllers.

### Tecnologias usadas nos testes

- JUnit
- Mockito
- MockMvc
- DataJpaTest
- Maven Test
- H2 Database para testes de persistência

---

### Testes de Entidade

Foram criados testes unitários para validar as regras internas das entidades.

Arquivos:

```txt
CategoriaTest
FeiranteTest
UsuarioTest
```

Esses testes validam regras como:

- categoria válida;
- nome de categoria com menos de 3 caracteres;
- descrição de categoria vazia;
- feirante válido;
- nome de feirante com menos de 3 caracteres;
- CPF vazio;
- CPF com quantidade inválida de dígitos;
- categoria obrigatória para feirante;
- usuário válido;
- nome de usuário inválido;
- e-mail obrigatório;
- senha com menos de 8 caracteres.

---

### Testes de Service com Mockito

Foram criados testes unitários para validar regras de negócio dos services, utilizando Mockito para simular os repositories e dependências externas.

Arquivos:

```txt
CategoriaServiceTest
FeiranteServiceTest
UsuarioServiceTest
AuthServiceTest
```

Esses testes validam regras como:

- criação de categoria com sucesso;
- bloqueio de categoria duplicada;
- listagem de categorias do usuário logado;
- remoção de categoria;
- bloqueio de remoção de categoria em uso;
- criação de feirante com sucesso;
- bloqueio de CPF duplicado;
- impedimento de usar categoria pertencente a outro usuário;
- listagem de feirantes do usuário logado;
- remoção de feirante;
- impedimento de remover feirante de outro usuário;
- listagem de usuários;
- remoção de usuário;
- erro ao remover usuário inexistente;
- cadastro de usuário;
- bloqueio de e-mail duplicado no cadastro;
- login com sucesso;
- erro ao tentar login com e-mail inexistente;
- erro ao tentar login com senha incorreta.

---

### Testes de Repository com @DataJpaTest

Foram criados testes para validar a camada de persistência com repositories reais, usando banco H2 em ambiente de teste.

Arquivos:

```txt
UsuarioRepositoryTest
CategoriaRepositoryTest
FeiranteRepositoryTest
```

Esses testes validam métodos como:

```txt
findByEmail
existsByEmail
findByUsuarioEmail
existsByNomeAndUsuarioEmail
existsByCpfAndUsuarioEmail
existsByCategoriaId
```

Também validam o isolamento de dados por usuário, garantindo que registros de um usuário não sejam retornados para outro.

---

### Testes de Controller com MockMvc

Foram criados testes para validar os endpoints dos controllers de forma isolada, utilizando MockMvc e services simulados com Mockito.

Arquivos:

```txt
AuthControllerTest
CategoriaControllerTest
FeiranteControllerTest
UsuarioControllerTest
```

Esses testes validam endpoints como:

```txt
POST /auth/cadastrar
POST /auth/login

GET /categoria
GET /categoria/{id}
POST /categoria
PUT /categoria/{id}
DELETE /categoria/{id}

GET /feirante
GET /feirante/{id}
POST /feirante
PUT /feirante/{id}
DELETE /feirante/{id}

GET /usuarios
DELETE /usuarios/{id}
```

Os testes verificam:

- status HTTP retornado;
- JSON de resposta;
- chamada correta dos services;
- criação, busca, atualização e remoção de recursos;
- login e cadastro via controller.

---

### Resultado Atual dos Testes

Resultado da última execução:

```txt
Tests run: 56
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

---

### Como Rodar os Testes

Na pasta do back-end:

```bash
cd Feira-BackEnd/feira-back
```

No Windows PowerShell:

```bash
.\mvnw test
```

Em Linux/macOS:

```bash
./mvnw test
```

---

## Deploy

### Front-end - Vercel

O front-end está publicado na Vercel:

```txt
https://feira-fullstack.vercel.app
```

A Vercel está conectada ao GitHub. A cada novo `git push`, ela inicia um novo deploy automaticamente.

### Back-end - Render

O back-end está publicado no Render:

```txt
https://feira-fullstack.onrender.com
```

O Render utiliza Docker para executar a aplicação Spring Boot.

Observação: por estar em plano gratuito, o back-end pode apresentar delay na primeira requisição após um período de inatividade.

---

## Variáveis de Ambiente

O projeto utiliza variável de ambiente para armazenar a chave do JWT.

Exemplo:

```txt
JWT_SECRET=sua-chave-secreta
```

No ambiente local, essa variável pode ser configurada no IntelliJ ou no sistema operacional.

No Render, ela deve ser cadastrada em:

```txt
Environment Variables
```

Essa abordagem evita deixar a chave secreta diretamente no código-fonte.

---

## Como Rodar Localmente

### Pré-requisitos

Antes de rodar o projeto, instale:

- Java 21
- Node.js
- npm
- Git
- Angular CLI
- IntelliJ IDEA ou outra IDE Java
- VS Code ou outro editor para o front-end

---

## Rodando o Back-end

Acesse a pasta do back-end:

```bash
cd Feira-BackEnd/feira-back
```

Configure a variável de ambiente `JWT_SECRET`.

Exemplo no Windows PowerShell:

```bash
$env:JWT_SECRET="sua-chave-secreta"
```

Depois execute:

```bash
.\mvnw spring-boot:run
```

O back-end ficará disponível em:

```txt
http://localhost:8080
```

Swagger local:

```txt
http://localhost:8080/swagger-ui/index.html
```

Console H2, se habilitado:

```txt
http://localhost:8080/h2-console
```

---

## Rodando o Front-end

Acesse a pasta do front-end:

```bash
cd Feira-FrontEnd/feira-front
```

Instale as dependências:

```bash
npm install
```

Execute o Angular:

```bash
ng serve
```

O front-end ficará disponível em:

```txt
http://localhost:4200
```

---

## Comandos Úteis

### Rodar back-end

```bash
cd Feira-BackEnd/feira-back
.\mvnw spring-boot:run
```

### Rodar testes do back-end

```bash
cd Feira-BackEnd/feira-back
.\mvnw test
```

### Rodar front-end

```bash
cd Feira-FrontEnd/feira-front
ng serve
```

### Build do front-end

```bash
cd Feira-FrontEnd/feira-front
npm run build
```

### Commit na raiz do projeto

```bash
git add .
git commit -m "Mensagem do commit"
git push
```

---

## Endpoints Principais

### Autenticação

```txt
POST /auth/cadastrar
POST /auth/login
```

### Categorias

```txt
GET /categoria
GET /categoria/{id}
POST /categoria
PUT /categoria/{id}
DELETE /categoria/{id}
```

### Feirantes

```txt
GET /feirante
GET /feirante/{id}
POST /feirante
PUT /feirante/{id}
DELETE /feirante/{id}
```

### Usuários

```txt
GET /usuarios
DELETE /usuarios/{id}
```

---

## Exemplos de Requisições

### Cadastro de usuário

```json
{
  "nome": "Leanderson Lima",
  "email": "leanderson@email.com",
  "senha": "12345678"
}
```

### Login

```json
{
  "email": "leanderson@email.com",
  "senha": "12345678"
}
```

Resposta esperada:

```json
{
  "token": "jwt-token",
  "nome": "Leanderson Lima",
  "tipo": "Bearer"
}
```

### Cadastro de categoria

```json
{
  "nome": "Verduras",
  "descricao": "Produtos verdes e hortaliças"
}
```

### Cadastro de feirante

```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "ativo": true,
  "categoriaId": 1
}
```

---

## Regras de Negócio

### Usuário

- Usuário pode se cadastrar.
- Usuário pode fazer login.
- Usuário autenticado recebe um token JWT.
- Rotas internas exigem autenticação.

### Categoria

- Nome da categoria é obrigatório.
- Nome da categoria deve ter pelo menos 3 caracteres.
- Descrição da categoria é obrigatória.
- Usuário não pode cadastrar categoria duplicada na própria conta.
- Usuário só pode listar suas próprias categorias.
- Usuário só pode editar suas próprias categorias.
- Usuário só pode excluir suas próprias categorias.
- Categoria em uso por feirante não pode ser removida.

### Feirante

- Nome do feirante é obrigatório.
- Nome do feirante deve ter pelo menos 3 caracteres.
- CPF é obrigatório.
- CPF deve ter exatamente 11 dígitos numéricos.
- Categoria é obrigatória.
- Usuário não pode cadastrar CPF duplicado na própria conta.
- Usuário só pode usar categorias da própria conta.
- Usuário só pode listar seus próprios feirantes.
- Usuário só pode editar seus próprios feirantes.
- Usuário só pode excluir seus próprios feirantes.

---

## Tratamento de Erros

O projeto possui tratamento de exceções para retornar mensagens adequadas ao front-end.

Exemplos de situações tratadas:

- dados inválidos;
- categoria duplicada;
- CPF duplicado;
- usuário não encontrado;
- categoria não encontrada;
- feirante não encontrado;
- tentativa de acessar dados de outro usuário;
- tentativa de remover categoria em uso.

---

## Diferenciais Técnicos

Este projeto possui diferenciais importantes para um projeto de portfólio:

- aplicação full stack completa;
- autenticação com JWT;
- Spring Security;
- proteção de rotas no Angular;
- interceptor HTTP;
- multiusuário;
- isolamento de dados por usuário;
- DTOs de request e response;
- arquitetura em camadas;
- tratamento global de exceções;
- validações de negócio;
- Swagger/OpenAPI;
- testes automatizados em múltiplas camadas: entity, service, repository e controller;
- JUnit, Mockito, MockMvc e @DataJpaTest;
- Docker;
- deploy na Vercel;
- deploy no Render;
- ambientes local e produção separados;
- responsividade mobile;
- dashboard dinâmico;
- Git e GitHub no fluxo de desenvolvimento.

---

## Possíveis Melhorias Futuras

Algumas melhorias que podem ser implementadas futuramente:

- migração do H2 para PostgreSQL;
- refresh token;
- roles de usuário, como ADMIN e USER;
- paginação;
- busca e filtros;
- ordenação;
- upload de foto para feirantes;
- ampliar testes de integração;
- testes end-to-end;
- CI/CD com GitHub Actions;
- melhorias de acessibilidade;
- logs estruturados;
- monitoramento;
- recuperação de senha;
- confirmação de e-mail;
- layout mobile mais avançado.

---

## Status do Projeto

Status atual:

```txt
Back-end: funcional
Front-end: funcional
Autenticação: funcional
CRUD: funcional
Multiusuário: funcional
Dashboard: funcional
Swagger: funcional
Testes: 56 testes passando
Deploy: funcional
Responsividade: implementada
Docker: implementado
```

O projeto está funcional, publicado e pronto para apresentação como projeto de portfólio.

---

## Autor

Desenvolvido por **Leanderson Lima**.

GitHub:

```txt
https://github.com/Leanderson137
```

LinkedIn:

```txt
https://www.linkedin.com/in/leandersonlima13
```

Email:

```txt
leandersonlima137@gmail.com
```