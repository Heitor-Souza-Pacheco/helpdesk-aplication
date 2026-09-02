# 💬 HelpDesk API

<p align="center">
  <strong>API REST para uma plataforma de perguntas e respostas com autenticação JWT.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white">
  <img src="https://img.shields.io/badge/JWT-Authentication-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
</p>

---

## 📖 Sobre o projeto

O **HelpDesk API** é uma API REST desenvolvida em **Java e Spring Boot** para uma plataforma de perguntas e respostas.

O projeto foi desenvolvido com foco em conceitos de **desenvolvimento backend, autenticação, autorização, persistência de dados e construção de APIs REST**.

A aplicação permite o gerenciamento de usuários e fornece uma estrutura para criação de uma plataforma semelhante a sistemas de fórum e helpdesk.

### 🎯 Objetivos

- Desenvolver uma API REST utilizando Spring Boot.
- Implementar autenticação utilizando **JWT**.
- Trabalhar com **Spring Security**.
- Persistir dados utilizando **Spring Data JPA e PostgreSQL**.
- Aplicar conceitos de arquitetura e organização de aplicações backend.
- Desenvolver endpoints protegidos por autenticação.

---

## ✨ Funcionalidades

### 🔐 Autenticação

- Cadastro de usuários.
- Login de usuários.
- Geração de token JWT.
- Proteção de endpoints utilizando Spring Security.

### 👤 Usuários

- Listagem de usuários.
- Criação de usuários.
- Atualização de usuários.
- Exclusão de usuários.
- Controle de acesso através de JWT.

### 🗄️ Persistência

- Integração com PostgreSQL.
- Mapeamento de entidades utilizando JPA/Hibernate.
- Operações CRUD.

---

## 🛠️ Tecnologias

### Backend

| Tecnologia | Utilização |
|---|---|
| ☕ Java 21 | Linguagem principal |
| 🌱 Spring Boot 4.0.6 | Framework backend |
| 🔐 Spring Security | Autenticação e autorização |
| 🎫 JJWT 0.12.6 | Geração e validação de JWT |
| 🗃️ Spring Data JPA | Persistência de dados |
| ⚙️ Hibernate | ORM |
| 🐘 PostgreSQL | Banco de dados |
| 📦 Maven | Gerenciamento de dependências |

---

## 🏗️ Arquitetura

A aplicação segue uma organização baseada em responsabilidades, separando as principais camadas da aplicação.

```text
src/
└── main/
    ├── java/
    │   └── ...
    │
    └── resources/
        └── application.properties
```

Entre os principais conceitos utilizados estão:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Além da camada de segurança responsável pelo processo de autenticação e autorização:

```text
Client
  │
  ▼
Login
  │
  ▼
JWT
  │
  ▼
Spring Security
  │
  ▼
Protected Endpoint
```

---

# 🔐 Autenticação

A API utiliza **JWT (JSON Web Token)** para autenticação.

O fluxo de autenticação funciona da seguinte maneira:

```text
┌──────────────┐
│    Cliente   │
└──────┬───────┘
       │
       │ Login
       ▼
┌──────────────┐
│   /auth/login│
└──────┬───────┘
       │
       │ JWT
       ▼
┌──────────────┐
│    Cliente   │
└──────┬───────┘
       │
       │ Authorization: Bearer <token>
       ▼
┌─────────────────────┐
│ Endpoint protegido  │
└─────────────────────┘
```

Os endpoints que exigem autenticação devem receber o token JWT no header:

```http
Authorization: Bearer <JWT>
```

---

# 📡 Endpoints

## 🔑 Autenticação

| Método | Rota | Descrição | Autenticação |
|---|---|---|---|
| `POST` | `/auth/register` | Cadastrar novo usuário | ❌ |
| `POST` | `/auth/login` | Realizar login e obter JWT | ❌ |

---

## 👤 Usuários

> 🔒 Os endpoints abaixo requerem autenticação JWT.

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/usuario` | Listar todos os usuários |
| `POST` | `/usuario` | Criar usuário |
| `PUT` | `/usuario` | Atualizar usuário |
| `DELETE` | `/usuario/{id}` | Excluir usuário |

---

# ⚙️ Configuração do ambiente

## 📋 Pré-requisitos

Antes de executar o projeto, tenha instalado:

- ☕ JDK 21 ou superior
- 🐘 PostgreSQL
- 📦 Maven
- Git

O PostgreSQL deve estar disponível na porta padrão:

```text
5432
```

---

# 🚀 Como executar

## 1. Clone o repositório

```bash
git clone <URL-DO-REPOSITORIO>
cd helpdesk-aplication
```

---

## 2. Configure as variáveis

Copie o arquivo de configuração de exemplo:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Depois, configure suas credenciais no arquivo:

```text
src/main/resources/application.properties
```

### Variáveis necessárias

| Variável | Descrição |
|---|---|
| `DB_USERNAME` | Usuário do PostgreSQL |
| `DB_PASSWORD` | Senha do PostgreSQL |
| `JWT_SECRET` | Chave utilizada para assinar os tokens JWT |

> ⚠️ Nunca publique credenciais, senhas ou chaves JWT reais no repositório.

---

## 3. Crie o banco de dados

Crie o banco utilizado pela aplicação:

```bash
createdb -U postgres serviceSite
```

Ou utilizando o PostgreSQL:

```sql
CREATE DATABASE serviceSite;
```

---

## 4. Execute a aplicação

Utilizando o Maven Wrapper:

### Linux / macOS

```bash
./mvnw spring-boot:run
```

### Windows

```bash
mvnw.cmd spring-boot:run
```

---

## 🌐 Acesso

Após iniciar a aplicação, a API estará disponível em:

```text
http://localhost:8083
```

---

# 🐳 Docker

O projeto também possui um `Dockerfile`, permitindo preparar a aplicação para execução em ambiente containerizado.

A estrutura relacionada ao Docker está presente na raiz do projeto:

```text
Dockerfile
```

> A configuração de containerização pode ser utilizada posteriormente para facilitar o deploy da aplicação.

---

# 📂 Estrutura do projeto

```text
helpdesk-aplication/
│
├── .mvn/
│   └── wrapper/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│
├── .gitignore
├── Dockerfile
├── DEPLOY-RENDER.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# 🧠 Conceitos praticados

Durante o desenvolvimento deste projeto foram trabalhados conceitos importantes de backend:

- APIs REST
- Java
- Spring Boot
- Spring Security
- Autenticação JWT
- Autorização
- CRUD
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Docker
- Variáveis de ambiente
- Arquitetura de aplicações backend
- Integração entre aplicação e banco de dados

---

# 📚 O que aprendi com o projeto

O desenvolvimento do HelpDesk API proporcionou experiência prática com a construção de uma aplicação backend completa, principalmente nos seguintes pontos:

- Desenvolvimento de APIs REST.
- Implementação de autenticação e autorização.
- Integração entre Spring Boot e PostgreSQL.
- Persistência de dados utilizando JPA/Hibernate.
- Organização de uma aplicação backend.
- Proteção de endpoints.
- Gerenciamento de dependências com Maven.
- Preparação da aplicação para containerização e deploy.

---

# 🔮 Próximos passos

Algumas funcionalidades que podem ser adicionadas futuramente:

- [ ] Sistema completo de perguntas.
- [ ] Sistema de respostas.
- [ ] Votos em perguntas e respostas.
- [ ] Categorias.
- [ ] Busca de perguntas.
- [ ] Paginação.
- [ ] Perfis de usuário.
- [ ] Diferentes níveis de permissão.
- [ ] Documentação com Swagger/OpenAPI.
- [ ] Testes automatizados.
- [ ] Deploy em produção.

---

# 👨‍💻 Desenvolvedor

**Heitor Souza Pacheco**

Estudante de Ensino Médio Técnico em Informática e desenvolvedor interessado em **Java, Spring Boot, APIs REST, Docker, Kubernetes e desenvolvimento de software**.

<p align="center">
  <a href="https://github.com/Heitor-Souza-Pacheco">
    <img src="https://img.shields.io/badge/GitHub-1E3A8A?style=for-the-badge&logo=github&logoColor=white">
  </a>
  <a href="https://linkedin.com/in/heitor-souza-pacheco">
    <img src="https://img.shields.io/badge/LinkedIn-1E3A8A?style=for-the-badge&logo=linkedin&logoColor=white">
  </a>
</p>

---

<p align="center">
  ⭐ Se este projeto foi útil ou interessante, considere deixar uma estrela!
</p>
