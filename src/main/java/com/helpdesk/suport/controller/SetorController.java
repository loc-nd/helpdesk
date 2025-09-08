package com.helpdesk.suport.controller;

import com.helpdesk.suport.models.dto.SetorCreateDTO;
import com.helpdesk.suport.models.dto.SetorResponseDTO;
import com.helpdesk.suport.models.dto.UsuarioLogadoDTO;
import com.helpdesk.suport.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService setorService;

    @PostMapping
    public ResponseEntity<SetorResponseDTO> criarSetor(@RequestBody @Valid SetorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(setorService.criarSetor(dto));
    }

    @GetMapping
    public ResponseEntity<List<SetorResponseDTO>> listarSetores() {
        return ResponseEntity.ok(setorService.listarSetores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorResponseDTO> atualizarSetor(@PathVariable Long id, @RequestBody @Valid SetorCreateDTO dto) {
        return ResponseEntity.ok(setorService.atualizarSetor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSetor(
            @PathVariable Long id,
            @RequestParam Long usuarioLogadoId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        setorService.deletarSetor(id, new UsuarioLogadoDTO(usuarioLogadoId, isAdmin));
        return ResponseEntity.noContent().build();
    }
}
