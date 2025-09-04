package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existisByNome(String nome);
}
