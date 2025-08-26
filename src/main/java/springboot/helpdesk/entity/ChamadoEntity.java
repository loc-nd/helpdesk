package springboot.helpdesk.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_CHAMADOS")
public class ChamadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private PrioridadeEnum prioridade;
    @Enumerated(EnumType.STRING)
    private StatusChamadoEnum status;
    private List<AnexoEntity> anexos = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "usuario_criador_id", nullable = false)
    private UsuarioEntity usuarioCriador;
    @ManyToOne
    @JoinColumn(name = "usuario_responsavel_id")
    private UsuarioEntity usuarioResponsavel;
    @ManyToMany
    @JoinTable(
            name = "TB_CHAMADOS_OBSERVADORES",
            joinColumns = @JoinColumn(name = "chamado_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<UsuarioEntity> observadores = new HashSet<>();
    private List<MensagemEntity> mensagens = new ArrayList<>();
}
