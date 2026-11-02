# Comunicação API
API responsável por agendar comunicações por e-mail, consultar o status
de envio e cancelar comunicações.

## Endpoints da Aplicação
- **GET /comunicacao?emailDestinatario={email}**: Retorna a comunicação do destinatário
  com o seu status de envio.

- **POST /comunicacao/agendar**: Cadastra uma nova comunicação por mensagem,
  com o status padrão `PENDENTE`.

  **Corpo da requisição (JSON):**
  ```json
  {
    "dataHoraEnvio": "yyyy-MM-dd HH:mm:ss",
    "nomeDestinatario": "nome",
    "emailDestinatario": "usuario@email.com",
    "telefoneDestinatario": "123456789",
    "mensagem": "texto da mensagem",
    "modoDeEnvio": ""
  }
  ```

- **PATCH /comunicacao/cancelar?emailDestinatario={email}**: Altera o status da
  comunicação para `CANCELADO`.

## Como executar com Docker

1. Copie `.env.example` para `.env` e defina a senha do banco.
2. Execute:
   ```bash
   docker compose up --build
   ```
3. Acesse o Swagger em http://localhost:8080/swagger-ui/index.html

Observação: o envio de e-mail depende da API de notificação, que deve estar
rodando em http://localhost:8082.

## Melhorias realizadas

- [x] Documentação dos endpoints
- [x] Swagger/OpenAPI
- [x] Integração com a API de notificação (Feign)
- [x] MapStruct na conversão DTO ↔ Entity
- [x] GlobalExceptionHandler
- [x] Docker e Docker Compose
- [ ] Testes unitários (Controller e Service)