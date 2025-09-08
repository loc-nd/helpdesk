package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    Optional<Chamado> findByTitulo(String nome);

    List<Chamado> findByUsuarioCriador(Usuario usuario);

    List<Chamado> findByUsuarioResponsavel(Usuario usuario);

    List<Chamado> findByUsuarioCriador_Setor(Setor setor);

    List<Chamado> findByStatus(StatusChamadoEnum status);

    List<Chamado> findByPrioridade(PrioridadeEnum prioridade);

    List<Chamado> findByStatusNot(StatusChamadoEnum status);

    List<Chamado> findByStatusAndPrioridade(StatusChamadoEnum status, PrioridadeEnum prioridade);

    List<Chamado> findByUsuarioCriador_SetorAndStatusNot(Setor setor, StatusChamadoEnum status);

}
