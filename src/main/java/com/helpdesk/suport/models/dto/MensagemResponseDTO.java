package com.helpdesk.suport.models.dto;

import java.time.LocalDateTime;

public record MensagemResponseDTO(
        Long id,
        String conteudo,
        String remetenteNome,
        Long chamadoId,
        LocalDateTime dataEnvio
) {
}
