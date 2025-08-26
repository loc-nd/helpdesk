package springboot.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.helpdesk.entity.MensagemEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface MensagemRepository extends JpaRepository<MensagemEntity, UUID> {

    List<MensagemEntity> findByChamadoId(UUID chamadoId);

    List<MensagemEntity> findByAutorId(UUID autorId);
}
