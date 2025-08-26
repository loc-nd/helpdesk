package springboot.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.helpdesk.entity.SetorEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SetorRepository extends JpaRepository<SetorEntity, UUID> {
    Optional<SetorEntity> findByNome(String nome);
}
