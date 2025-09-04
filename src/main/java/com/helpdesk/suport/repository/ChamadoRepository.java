package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.Chamado;
import com.helpdesk.suport.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    Optional<Chamado> findById(Long id);

    Optional<Chamado> findByTitulo(String nome);

    Optional<Chamado> findByUsuarioCriador(Usuario usuario);

    Optional<Chamado> findByUsuarioResponsavel(Usuario usuario);


}
