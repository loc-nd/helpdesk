package com.helpdesk.suport.controller;

import com.helpdesk.suport.models.dto.UsuarioCreateDTO;
import com.helpdesk.suport.models.dto.UsuarioLogadoDTO;
import com.helpdesk.suport.models.dto.UsuarioResponseDTO;
import com.helpdesk.suport.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(dto));
    }

    @PutMapping("/{id}/remover-setor")
    public ResponseEntity<UsuarioResponseDTO> removerSetorDoUsuario(
            @PathVariable Long id,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        var res = usuarioService.removerUsuarioDoSetor(id, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(
            @PathVariable Long id,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin){

        usuarioService.deletarUsuario(id, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.noContent().build();
    }
}
