package com.luizalebs.comunicacao_api.infraestructure.exceptions;

import java.time.LocalDateTime;

public record ErroResponseDTO(LocalDateTime timestamp, int status, String mensagem) {}
