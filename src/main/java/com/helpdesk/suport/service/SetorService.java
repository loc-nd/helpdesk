package com.helpdesk.suport.service;

import com.helpdesk.suport.exception.SetorNaoEncontradoException;
import com.helpdesk.suport.models.dto.SetorCreateDTO;
import com.helpdesk.suport.models.dto.SetorResponseDTO;
import com.helpdesk.suport.models.entity.Setor;
import com.helpdesk.suport.models.entity.Usuario;
import com.helpdesk.suport.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetorService {


    private final SetorRepository setorRepository;


    public SetorResponseDTO criarSetor(SetorCreateDTO dto) {
        Setor setor = new Setor();
        setor.setNome(dto.nome());
        return toResponseDTO(setorRepository.save(setor));
    }

    public List<SetorResponseDTO> listarSetores() {
        return setorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public SetorResponseDTO atualizarSetor(Long id, SetorCreateDTO dto) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new SetorNaoEncontradoException("Setor não encontrado"));
        setor.setNome(dto.nome());
        return toResponseDTO(setorRepository.save(setor));
    }

    public void deletarSetor(Long id) {
        setorRepository.deleteById(id);
    }

    private SetorResponseDTO toResponseDTO(Setor setor) {
        List<String> nomesUsuarios = setor.getUsuarios().stream()
                .map(Usuario::getNome)
                .toList();
        return new SetorResponseDTO(setor.getId(), setor.getNome(), nomesUsuarios);
    }
}

