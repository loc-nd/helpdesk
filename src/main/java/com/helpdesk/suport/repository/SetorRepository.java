package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {

    Optional<Setor> findById(Long id);

    Optional<Setor> findByNome(String nome);

}
