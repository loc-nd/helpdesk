package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ChamadoCreateDTO(

        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotBlank String prioridade,
        @NotNull Long usuarioCriador,
        Set<Long> observadoresIds

){}
