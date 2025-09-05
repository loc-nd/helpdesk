package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.Chamado;
import com.helpdesk.suport.models.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
}
