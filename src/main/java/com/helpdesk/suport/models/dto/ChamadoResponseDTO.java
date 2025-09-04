package com.helpdesk.suport.models.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record ChamadoResponseDTO(

        Long id,
        String titulo,
        String descricao,
        String prioridade,
        String status,
        String criadorNome,
        String responsavelNome,
        Set<String> observadores,
        LocalDateTime dataCriacao
) {
}
