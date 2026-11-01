package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comunicacao")
@Tag(name = "Comunicação", description = "Cadastro e alteração de de status de mensagens")
public class ComunicacaoController {

    private final ComunicacaoService service;

    public ComunicacaoController(ComunicacaoService service) {
        this.service = service;
    }

    @PostMapping("/agendar")
    @Operation(summary = "Cadastrar uma nova comunicação por mensagem", description = "Cadastra uma nova comunicação por mensagem, com o status padrão PENDENTE")
    @ApiResponse(responseCode = "200", description = "Mensagem cadastrada com sucesso!")
    @ApiResponse(responseCode = "409", description = "Usuario já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<ComunicacaoOutDTO> agendar(@RequestBody ComunicacaoInDTO dto)  {
        return ResponseEntity.ok(service.agendarComunicacao(dto));
    }

    @GetMapping()
    @Operation(summary = "Retornar todas as comunicações cadastradas", description = "Retorna a comunicação do destinatário com o seu status de envio.")
    @ApiResponse(responseCode = "200", description = "Mensagem/ns encontradas com sucesso!")
    @ApiResponse(responseCode = "403", description = "Mensagem não cadastrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<ComunicacaoOutDTO> buscarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }

    @PatchMapping("/cancelar")
    @Operation(summary = "Alterar status de mensagem", description = " Altera o status da comunicação para CANCELADO.")
    @ApiResponse(responseCode = "200", description = "Status alterado com sucesso")
    @ApiResponse(responseCode = "403", description = "Mensagem não cadastrada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<ComunicacaoOutDTO> cancelarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }
}
