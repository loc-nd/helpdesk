package com.helpdesk.suport.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "TB_USUARIOS")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    @OneToOne
    private Setor setor;
    @OneToMany
    private List<Chamado> chamadosCriados = new ArrayList<>();
    @ManyToOne
    private Usuario usuarioResponsavel;
    @ManyToMany
    private Set<Chamado> chamadosObservados = new HashSet<>();
}
