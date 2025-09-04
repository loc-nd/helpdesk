package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.NotBlank;

public record SetorCreateDTO(

        @NotBlank String nome
)
{}
