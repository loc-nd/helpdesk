package springboot.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.helpdesk.entity.AnexoEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnexoRepository extends JpaRepository<AnexoEntity, UUID> {

    List<AnexoEntity> findByChamadoId(UUID chamadoId);

    List<AnexoEntity> findByUsuarioUpload(UUID usuarioId);
}
