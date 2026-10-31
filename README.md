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
