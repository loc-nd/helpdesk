package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChamadoDTO(

        @NotBlank
        String titulo,
        @NotBlank
        String descricao,
        @NotBlank
        String prioridade,
        String status,
        @NotBlank
        String usuarioCriador,
        String usuarioResponsavel

){}
