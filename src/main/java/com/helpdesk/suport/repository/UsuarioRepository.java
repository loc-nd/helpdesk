package com.helpdesk.suport.repository;

import com.helpdesk.suport.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Long id); // se quiser, mas já vem do JpaRepository
    Optional<Usuario> findByEmail(String email);

}
