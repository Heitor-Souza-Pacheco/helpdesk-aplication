# HelpDesk API

API REST para o sistema HelpDesk — plataforma de perguntas e respostas com autenticação JWT.

## Tecnologias

- Java 21
- Spring Boot 4.0.6
- Spring Security + JWT (jjwt 0.12.6)
- Spring Data JPA + Hibernate
- PostgreSQL
- Maven

## Como rodar localmente

### Pré-requisitos
- JDK 21+
- PostgreSQL rodando na porta 5432

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/helpdesk-api.git
cd helpdesk-api
```

### 2. Configure as credenciais
Copie o arquivo de exemplo e preencha com seus dados:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edite o `application.properties` com seu usuário, senha do PostgreSQL e uma chave JWT.

### 3. Crie o banco de dados
```bash
createdb -U postgres serviceSite
```

### 4. Rode a aplicação
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8083`.

## Endpoints

### Autenticação
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/register` | Cadastrar novo usuário |
| POST | `/auth/login` | Login — retorna JWT |

### Usuários (requer JWT)
| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/usuario` | Listar todos os usuários |
| POST | `/usuario` | Criar usuário |
| PUT | `/usuario` | Atualizar usuário |
| DELETE | `/usuario/{id}` | Excluir usuário |

## Variáveis de ambiente necessárias

| Variável | Descrição |
|----------|-----------|
| `DB_USERNAME` | Usuário do PostgreSQL |
| `DB_PASSWORD` | Senha do PostgreSQL |
| `JWT_SECRET` | Chave secreta para assinar os tokens JWT (mín. 64 caracteres) |
