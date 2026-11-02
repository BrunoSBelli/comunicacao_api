package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.converter.ComunicacaoConverter;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.BusinessException;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service

public class ComunicacaoService {

    private final ComunicacaoRepository repository;
    private final ComunicacaoConverter converter;

    public ComunicacaoService(ComunicacaoRepository repository, ComunicacaoConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    public ComunicacaoOutDTO agendarComunicacao(ComunicacaoInDTO dto) {
        // Sem esta checagem, o banco recusa o INSERT e o cliente recebe um 500
        if (repository.existsByEmailDestinatario(dto.getEmailDestinatario())) {
            throw new BusinessException(
                    "Já existe uma comunicação para o e-mail: " + dto.getEmailDestinatario());
        }
        dto.setStatusEnvio(StatusEnvioEnum.PENDENTE);
        ComunicacaoEntity entity = converter.paraEntity(dto);
        return converter.paraDTO(repository.save(entity));
    }

    public ComunicacaoOutDTO buscarStatusComunicacao(String emailDestinatario) {
        return converter.paraDTO(buscarPorEmail(emailDestinatario));
    }

    public ComunicacaoOutDTO alterarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = buscarPorEmail(emailDestinatario);
        entity.setStatusEnvio(StatusEnvioEnum.CANCELADO);
        return converter.paraDTO(repository.save(entity));
    }

    public ComunicacaoOutDTO marcarComoEnviada(String emailDestinatario) {
        ComunicacaoEntity entity = buscarPorEmail(emailDestinatario);
        entity.setStatusEnvio(StatusEnvioEnum.ENVIADO);
        return converter.paraDTO(repository.save(entity));
    }

    private ComunicacaoEntity buscarPorEmail(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException(
                    "Comunicação não encontrada para o e-mail: " + emailDestinatario);
        }
        return entity;
    }

}
