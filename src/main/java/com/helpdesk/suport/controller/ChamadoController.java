package com.helpdesk.suport.controller;

import com.helpdesk.suport.models.dto.*;
import com.helpdesk.suport.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping
    public ResponseEntity<ChamadoResponseDTO> criarChamado(
            @RequestBody @Valid ChamadoCreateDTO dto,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        var res = chamadoService.criarChamado(dto, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoResponseDTO>> listarChamados(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String prioridade,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        ChamadoFiltroDTO filtro = new ChamadoFiltroDTO(status, prioridade);
        var lista = chamadoService.listarChamados(filtro, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorUsuario(
            @PathVariable Long userId,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        var lista = chamadoService.listarChamadosPorUsuario(userId, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/setor/{setorId}")
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorSetor(
            @PathVariable Long setorId,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        var lista = chamadoService.listarChamadosPorSetor(setorId, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/atribuir/{responsavelId}")
    public ResponseEntity<ChamadoResponseDTO> atribuirResponsavel(
            @PathVariable Long id,
            @PathVariable Long responsavelId,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        var res = chamadoService.atribuirResponsavel(id, responsavelId, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/concluir")
    public ResponseEntity<ChamadoResponseDTO> concluirChamado(
            @PathVariable Long id,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        var res = chamadoService.concluirChamado(id, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/{id}/mensagens")
    public ResponseEntity<MensagemResponseDTO> enviarMensagem(
            @PathVariable Long id,
            @RequestBody @Valid MensagemCreateDTO dto,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        // dica: você pode validar se dto.chamadoId == id, se quiser
        var res = chamadoService.enviarMensagem(dto, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
