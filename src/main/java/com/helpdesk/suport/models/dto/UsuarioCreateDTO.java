package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioCreateDTO(

        @NotBlank  String nome,
        @Email String email,
        @NotNull Long setorId
        )
{}
