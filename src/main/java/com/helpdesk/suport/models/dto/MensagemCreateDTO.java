package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemCreateDTO(
        @NotBlank String conteudo,
        @NotNull Long usuarioRemetenteId,
        @NotNull Long chamadoId
) {
}
