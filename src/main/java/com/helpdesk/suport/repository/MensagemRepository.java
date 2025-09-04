package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Chamado, Long> {
}
