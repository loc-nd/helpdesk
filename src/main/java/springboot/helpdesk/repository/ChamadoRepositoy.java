package springboot.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.helpdesk.entity.ChamadoEntity;
import springboot.helpdesk.entity.StatusChamadoEnum;
import springboot.helpdesk.entity.UsuarioEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChamadoRepositoy extends JpaRepository<ChamadoRepositoy, UUID> {

    List<ChamadoEntity> findByStatus(StatusChamadoEnum status);

    List<ChamadoEntity> findByUsuarioResponsavel(UUID usuario);

    List<ChamadoEntity> findBySetorNome(String nomeSetor);
}
