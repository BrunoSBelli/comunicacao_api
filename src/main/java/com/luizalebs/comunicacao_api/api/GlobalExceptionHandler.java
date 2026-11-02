package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.infraestructure.exceptions.BusinessException;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ErroResponseDTO;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErroResponseDTO> handleNaoEncontrado(ResourceNotFoundException ex) {
        return resposta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErroResponseDTO> handleRegraDeNegocio(BusinessException ex) {
        return resposta(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErroResponseDTO> handleNotificacao(FeignException ex) {
        // O cliente continua vendo a mensagem genérica; o motivo real fica no log
        log.error("Falha na chamada à notificação: {}", ex.getMessage());
        return resposta(HttpStatus.BAD_GATEWAY,
                "Falha ao se comunicar com o serviço de notificação.");
    }

    // Evita repetir a montagem do corpo em cada handler
    private ResponseEntity<ErroResponseDTO> resposta(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status)
                .body(new ErroResponseDTO(LocalDateTime.now(), status.value(), mensagem));
    }
}