package com.helpdesk.suport.service;

import com.helpdesk.suport.exception.BusinessException;
import com.helpdesk.suport.exception.SetorNaoEncontradoException;
import com.helpdesk.suport.exception.UsuarioNaoEncontradoException;
import com.helpdesk.suport.models.dto.UsuarioCreateDTO;
import com.helpdesk.suport.models.dto.UsuarioLogadoDTO;
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


    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO dto) {
        Setor setor = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new SetorNaoEncontradoException("Setor não encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSetor(setor);

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO removerUsuarioDoSetor(Long usuarioId, UsuarioLogadoDTO usuarioLogado) {
        if (!usuarioLogado.isAdmin()) {
            throw new BusinessException("Apenas administradores podem remover usuários de setores");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (usuario.getSetor() == null) {
            throw new BusinessException("Usuário não está vinculado a nenhum setor");
        }

        usuario.setSetor(null);
        Usuario salvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getSetor() != null ? salvo.getSetor().getNome() : null
        );
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        Setor setor = setorRepository.findById(dto.setorId())
                .orElseThrow(() -> new SetorNaoEncontradoException("Setor não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSetor(setor);

        return toResponseDTO(usuarioRepository.save(usuario));
    }

    public void deletarUsuario(Long usuarioId, UsuarioLogadoDTO usuarioLogado) {
        if (!usuarioLogado.isAdmin()) {
            throw new BusinessException("Apenas administradores podem excluir usuários");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (usuario.getSetor() != null) {
            throw new BusinessException("Não é possível excluir usuário vinculado a um setor");
        }

        usuarioRepository.deleteById(usuarioId);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSetor().getNome());
    }
}
