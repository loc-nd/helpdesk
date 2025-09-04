package com.helpdesk.suport.models.dto;

import java.util.List;

public record SetorResponseDTO(

        Long id,
        String nome,
        List<String> usuarios

) {
}
