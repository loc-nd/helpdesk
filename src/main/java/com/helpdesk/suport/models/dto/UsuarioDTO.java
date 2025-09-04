package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(

        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String setor
        ) {
}
