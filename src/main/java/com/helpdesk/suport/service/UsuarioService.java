package com.helpdesk.suport.service;

import com.helpdesk.suport.exception.SetorNaoEncontradoException;
import com.helpdesk.suport.exception.UsuarioNaoEncontradoException;
import com.helpdesk.suport.models.dto.UsuarioCreateDTO;
import com.helpdesk.suport.models.dto.UsuarioResponseDTO;
import com.helpdesk.suport.models.entity.Setor;
import com.helpdesk.suport.models.entity.Usuario;
import com.helpdesk.suport.repository.SetorRepository;
import com.helpdesk.suport.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;


    public UsuarioResponseDTO criar(UsuarioCreateDTO dto) {
        Setor setor = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new SetorNaoEncontradoException("Setor não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSetor(setor);

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        Setor setor = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new SetorNaoEncontradoException("Setor não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSetor(setor);

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSetor().getNome());
    }
}
