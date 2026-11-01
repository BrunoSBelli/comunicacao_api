package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.api.dto.EmailRequestDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicacaoConverter;
import com.luizalebs.comunicacao_api.infraestructure.client.NotificacaoClient;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.BusinessException;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

   private final NotificacaoClient notificacaoClient;
   private final ComunicacaoService comunicacaoService;
   private final ComunicacaoRepository comunicacaoRepository;
   private final ComunicacaoConverter comunicacaoConverter;

    public void enviaEmail(String emailDestinatario) {
        ComunicacaoOutDTO comunicacao =
                comunicacaoService.buscarStatusComunicacao(emailDestinatario);

        validarStatusParaEnvio(comunicacao.getStatusEnvio());

        EmailRequestDTO request = EmailRequestDTO.builder()
                .emailUsuario(comunicacao.getEmailDestinatario())
                .nomeTarefa(comunicacao.getNomeDestinatario())
                .descricao(comunicacao.getMensagem())
                .dataEvento(converterData(comunicacao.getDataHoraEnvio()))
                .build();

        notificacaoClient.enviarEmail(request);

        // Só chega aqui se o envio não lançou exceção
        comunicacaoService.marcarComoEnviada(emailDestinatario);
    }

    private void validarStatusParaEnvio(StatusEnvioEnum status) {
        if (status == StatusEnvioEnum.CANCELADO) {
            throw new BusinessException("Comunicação cancelada: o e-mail não foi enviado.");
        }
        if (status == StatusEnvioEnum.ENVIADO) {
            throw new BusinessException("Esta comunicação já foi enviada.");
        }
    }

        private LocalDateTime converterData(Date data) {
        if (data == null) {
            return null;
        }
        return data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
