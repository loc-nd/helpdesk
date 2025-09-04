package com.helpdesk.suport.models.dto;

import jakarta.validation.constraints.Email;

public record SetorDTO (

        @Email
        String nome
)
{}
