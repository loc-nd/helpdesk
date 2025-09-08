package com.helpdesk.suport.service;

import com.helpdesk.suport.exception.BusinessException;
import com.helpdesk.suport.exception.ChamadoNaoEncontradoException;
import com.helpdesk.suport.exception.SetorNaoEncontradoException;
import com.helpdesk.suport.exception.UsuarioNaoEncontradoException;
import com.helpdesk.suport.models.dto.*;
import com.helpdesk.suport.models.entity.*;
import com.helpdesk.suport.repository.ChamadoRepository;
import com.helpdesk.suport.repository.MensagemRepository;
import com.helpdesk.suport.repository.SetorRepository;
import com.helpdesk.suport.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemRepository mensagemRepository;
    private final SetorRepository setorRepository;


    public ChamadoResponseDTO criarChamado(ChamadoCreateDTO dto, UsuarioLogadoDTO usuarioLogado) {
        Usuario criador = usuarioRepository.findById(dto.usuarioCriador())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário criador não encontrado"));

        if (!usuarioLogado.isAdmin() && !usuarioLogado.id().equals(criador.getId())) {
            throw new BusinessException("Usuário não autorizado a criar chamado para outro usuário");
        }

        Chamado chamado = new Chamado();
        chamado.setTitulo(dto.titulo());
        chamado.setDescricao(dto.descricao());
        chamado.setPrioridade(PrioridadeEnum.valueOf(dto.prioridade()));
        chamado.setStatus(StatusChamadoEnum.ABERTO);
        chamado.setUsuarioCriador(criador);

        if (dto.observadoresIds() != null) {
            Set<Usuario> observadores = dto.observadoresIds().stream()
                    .map(id -> usuarioRepository.findById(id)
                            .orElseThrow(() -> new UsuarioNaoEncontradoException("Observador não encontrado: " + id)))
                    .collect(Collectors.toSet());
            chamado.setObservadores(observadores);
        }

        Chamado salvo = chamadoRepository.save(chamado);
        return toResponseDTO(salvo);
    }


    public ChamadoResponseDTO concluirChamado(Long chamadoId, UsuarioLogadoDTO usuarioLogado) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado"));

        if (!usuarioLogado.isAdmin() &&
                (chamado.getUsuarioResponsavel() == null ||
                        !chamado.getUsuarioResponsavel().getId().equals(usuarioLogado.id()))) {
            throw new BusinessException("Usuário não autorizado a concluir este chamado");
        }

        if (chamado.getStatus() != StatusChamadoEnum.EM_ATENDIMENTO) {
            throw new BusinessException("Só é possível concluir chamados em atendimento");
        }

        chamado.setStatus(StatusChamadoEnum.CONCLUIDO);
        return toResponseDTO(chamadoRepository.save(chamado));
    }


    public ChamadoResponseDTO atribuirResponsavel(Long chamadoId, Long responsavelId, UsuarioLogadoDTO usuarioLogado) {

        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado"));

        Usuario responsavel = usuarioRepository.findById(responsavelId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário responsável não encontrado"));


        if (!usuarioLogado.isAdmin()) {
            Usuario usuario = usuarioRepository.findById(usuarioLogado.id())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário logado não encontrado"));

            if (!usuario.getSetor().equals(chamado.getUsuarioCriador().getSetor())) {
                throw new BusinessException("Usuário não autorizado a atribuir responsável para chamados de outro setor");
            }
        }

        chamado.setUsuarioResponsavel(responsavel);
        chamado.setStatus(StatusChamadoEnum.EM_ATENDIMENTO);

        return toResponseDTO(chamadoRepository.save(chamado));

    }


    public ChamadoResponseDTO atribuirObservador(Long chamadoId, Long observadorId, UsuarioLogadoDTO usuarioLogado) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado"));

        Usuario observador = usuarioRepository.findById(observadorId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Observador não encontrado"));

        // Regras de permissão
        if (!usuarioLogado.isAdmin()) {
            boolean ehCriador = chamado.getUsuarioCriador().getId().equals(usuarioLogado.id());
            boolean ehResponsavel = chamado.getUsuarioResponsavel() != null &&
                    chamado.getUsuarioResponsavel().getId().equals(usuarioLogado.id());

            if (!ehCriador && !ehResponsavel) {
                throw new BusinessException("Somente criador, responsável ou administrador podem adicionar observadores");
            }
        }

        chamado.getObservadores().add(observador);
        return toResponseDTO(chamadoRepository.save(chamado));
    }


    public MensagemResponseDTO enviarMensagem(MensagemCreateDTO dto, UsuarioLogadoDTO usuarioLogado) {
        Chamado chamado = chamadoRepository.findById(dto.chamadoId())
                .orElseThrow(() -> new ChamadoNaoEncontradoException("Chamado não encontrado"));

        Usuario remetente = usuarioRepository.findById(dto.usuarioRemetenteId())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário remetente não encontrado"));

        boolean autorizado = usuarioLogado.isAdmin()
                || chamado.getUsuarioCriador().getId().equals(remetente.getId())
                || (chamado.getUsuarioResponsavel() != null &&
                chamado.getUsuarioResponsavel().getId().equals(remetente.getId()))
                || chamado.getObservadores().stream().anyMatch(o -> o.getId().equals(remetente.getId()));

        if (!autorizado) {
            throw new BusinessException("Usuário não autorizado a enviar mensagem neste chamado");
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setConteudo(dto.conteudo());
        mensagem.setUsuarioRemetente(remetente);
        mensagem.setChamado(chamado);
        mensagem.setDataEnvio(LocalDateTime.now());

        Mensagem salva = mensagemRepository.save(mensagem);

        return new MensagemResponseDTO(
                salva.getId(),
                salva.getConteudo(),
                remetente.getNome(),
                chamado.getId(),
                salva.getDataEnvio()
        );
    }


    public List<ChamadoResponseDTO> listarChamados(String status, String prioridade, UsuarioLogadoDTO usuarioLogado) {
        List<Chamado> chamados;

        if (usuarioLogado.isAdmin()) {
            // Admin pode filtrar
            if (status != null && prioridade != null) {
                chamados = chamadoRepository.findByStatusAndPrioridade(
                        StatusChamadoEnum.valueOf(status),
                        PrioridadeEnum.valueOf(prioridade)
                );
            } else if (status != null) {
                chamados = chamadoRepository.findByStatus(StatusChamadoEnum.valueOf(status));
            } else if (prioridade != null) {
                chamados = chamadoRepository.findByPrioridade(PrioridadeEnum.valueOf(prioridade));
            } else {
                chamados = chamadoRepository.findByStatusNot(StatusChamadoEnum.CONCLUIDO);
            }
        } else {
            // Usuário comum só vê chamados do próprio setor
            Usuario usuario = usuarioRepository.findById(usuarioLogado.id())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

            chamados = chamadoRepository.findByUsuarioCriador_Setor(usuario.getSetor());
        }

        return chamados.stream()
                .map(this::toResponseDTO)
                .toList();
    }


    public List<ChamadoResponseDTO> listarChamadosPorUsuarioCriador(Long usuarioId, UsuarioLogadoDTO usuarioLogado) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!usuarioLogado.isAdmin() &&
                !usuarioLogado.id().equals(usuarioId) &&
                !usuarioLogado.id().equals(usuario.getSetor().getId())) {
            throw new BusinessException("Usuário não autorizado a listar chamados de outro setor");
        }

        return chamadoRepository.findByUsuarioCriador(usuario).stream()
                .map(this::toResponseDTO)
                .toList();
    }


    public List<ChamadoResponseDTO> listarChamadosPorSetorCriador(Long setorId, UsuarioLogadoDTO usuarioLogado) {
        if (!usuarioLogado.isAdmin()) {
            throw new BusinessException("Apenas administradores podem listar chamados por setor");
        }

        Setor setor = setorRepository.findById(setorId)
                .orElseThrow(() -> new SetorNaoEncontradoException("Setor não encontrado"));

        return chamadoRepository.findByUsuarioCriador_Setor(setor).stream()
                .map(this::toResponseDTO)
                .toList();
    }


    private ChamadoResponseDTO toResponseDTO(Chamado ch) {
        return new ChamadoResponseDTO(
                ch.getId(),
                ch.getTitulo(),
                ch.getDescricao(),
                ch.getPrioridade().name(),
                ch.getStatus().name(),
                ch.getUsuarioCriador().getNome(),
                ch.getUsuarioResponsavel() != null ? ch.getUsuarioResponsavel().getNome() : null,
                ch.getObservadores().stream().map(Usuario::getNome).collect(Collectors.toSet()),
                LocalDateTime.now()
        );
    }
}
