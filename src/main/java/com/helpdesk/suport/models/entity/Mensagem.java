package com.helpdesk.suport.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "TB_MENSAGENS")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;
    @Column(nullable = false)
    private LocalDateTime dataEnvio =  LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario usuarioAutor;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioRemetente;
    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;
}
