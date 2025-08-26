package springboot.helpdesk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MensagemEntity {

    @Id
    private UUID id;
    private UsuarioEntity autor;
    private String texto;
    private LocalDateTime dataEnvio;
}
