package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.NotificacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@Tag(name = "Notificação", description = "Envio de mensagens")
public class NotificacaoController {

    private final NotificacaoService notificacaoServiceService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestParam String emailDestinatario) {
        notificacaoServiceService.enviaEmail(emailDestinatario);
        return ResponseEntity.ok().build();
    }

}
