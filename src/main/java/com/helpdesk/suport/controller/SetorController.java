package com.helpdesk.suport.controller;

import com.helpdesk.suport.models.dto.SetorCreateDTO;
import com.helpdesk.suport.models.dto.SetorResponseDTO;
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
    public ResponseEntity<SetorResponseDTO> criar(@RequestBody @Valid SetorCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(setorService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<SetorResponseDTO>> listar() {
        return ResponseEntity.ok(setorService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid SetorCreateDTO dto) {
        return ResponseEntity.ok(setorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        setorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
