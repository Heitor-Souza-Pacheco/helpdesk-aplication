# Deploy no Render - HelpDesk API (Gratuito)

## Pré-requisitos

- Conta no [Render](https://render.com) (cadastro gratuito, sem cartão)
- Repositório no GitHub com o código da API

---

## Passo 1: Subir o código no GitHub

Se ainda não fez, crie um repositório e faça push:

```bash
git add .
git commit -m "preparar para deploy no Render"
git push origin main
```

---

## Passo 2: Criar o banco PostgreSQL no Render

1. Acesse https://dashboard.render.com
2. Clique em **New > PostgreSQL**
3. Configure:
   - Name: `helpdesk-db`
   - Database: `serviceSite`
   - User: `postgres` (ou deixe o padrão)
   - Region: Oregon (ou a mais próxima)
   - Plan: **Free**
4. Clique em **Create Database**
5. Após criado, copie as credenciais:
   - **Internal Database URL** (para conectar do Web Service)
   - Ou copie separadamente: Host, Port, Database, Username, Password

---

## Passo 3: Criar o Web Service

1. No dashboard, clique em **New > Web Service**
2. Conecte seu repositório GitHub
3. Configure:
   - Name: `helpdesk-api`
   - Region: mesma do banco
   - Runtime: **Docker**
   - Plan: **Free**
4. Em **Environment Variables**, adicione:

| Variável | Valor |
|----------|-------|
| `DB_URL` | `jdbc:postgresql://HOST:PORT/serviceSite` (use o Internal Host do banco) |
| `DB_USERNAME` | username do banco |
| `DB_PASSWORD` | password do banco |
| `JWT_SECRET` | `bxOksa8BHgdAhR80Y3pEYvS5M+MnF2sheFDqprkTqQ4odqoszJLW1ikw64/nT/dTvlgrcBTq7HfK1B9Gai2h5A==` |
| `PORT` | `8083` |
| `IA_GEMINI_API_KEY` | sua chave (opcional) |

5. Clique em **Create Web Service**

---

## Passo 4: Aguardar o deploy

O Render vai:
1. Clonar o repositório
2. Buildar com o Dockerfile (Maven + Java 21)
3. Iniciar a aplicação

Acompanhe nos logs. Quando aparecer "Started DemoApplication", está pronto.

---

## Passo 5: Testar

A URL será algo como:
```
https://helpdesk-api-xxxx.onrender.com
```

Teste com:
```
POST https://helpdesk-api-xxxx.onrender.com/auth/login
Content-Type: application/json

{"username": "seu@email.com", "password": "suaSenha"}
```

---

## Passo 6: Atualizar o frontend

No arquivo `assets/js/global/api.js` do frontend, troque:

```javascript
const API_BASE_URL = 'https://helpdesk-api-xxxx.onrender.com';
```

---

## Observações importantes

- **Free tier**: O serviço "dorme" após 15 min sem requisições. A primeira requisição após inatividade leva ~30s para responder.
- **Banco grátis**: Expira após 90 dias. Você pode recriar outro grátis.
- **Deploy automático**: Toda vez que fizer push no GitHub, o Render re-deploya automaticamente.

---

## Comandos úteis (local)

```bash
# Gerar build local
mvnw.cmd clean package -DskipTests

# Testar o Docker localmente (se tiver Docker instalado)
docker build -t helpdesk-api .
docker run -p 8083:8083 -e DB_URL=jdbc:postgresql://host:5432/serviceSite -e DB_USERNAME=postgres -e DB_PASSWORD=senha -e JWT_SECRET=chave helpdesk-api
```
