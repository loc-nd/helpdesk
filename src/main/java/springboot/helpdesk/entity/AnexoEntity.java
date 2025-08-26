package springboot.helpdesk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_ANEXOS")
public class AnexoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nomeArquivo;
    private Long tamanhoBytes;
    private String contentType;
    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    private ChamadoEntity chamado;
    @ManyToOne
    @JoinColumn(name = "usuario_upload_id", nullable = false)
    private UsuarioEntity usuarioUpload;
}
