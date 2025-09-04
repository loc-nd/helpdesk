package com.helpdesk.suport.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "TB_CHAMADOS")
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private PrioridadeEnum prioridade;
    @Enumerated(EnumType.STRING)
    private StatusChamadoEnum status;
    @ManyToOne
    @JoinColumn(name = "criador_id")
    private Usuario usuarioCriador;
    @ManyToOne
    @JoinColumn(name = "usuario_responsavel_id")
    private Usuario usuarioResponsavel;
    @ManyToMany
    @JoinTable(
            name = "TB_CHAMADOS_OBSERVADORES",
            joinColumns = @JoinColumn(name = "chamado_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> observadores = new HashSet<>();
}
