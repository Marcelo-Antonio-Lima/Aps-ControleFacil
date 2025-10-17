package ControleFacil_Backend.controllers;

import ControleFacil_Backend.dtos.GrupoDTOs.GrupoCreateDTO;
import ControleFacil_Backend.dtos.GrupoDTOs.GrupoResponseDTO;
import ControleFacil_Backend.dtos.GrupoDTOs.GrupoUpdateDTO;
import ControleFacil_Backend.models.Grupo;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.services.GrupoService;
import ControleFacil_Backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "*")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<GrupoResponseDTO> criarGrupo(@Valid @RequestBody GrupoCreateDTO dto) {
        Optional<Usuario> criador = usuarioService.buscarPorId(dto.getCriadorId());
        if (criador.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Grupo grupo = new Grupo();
        grupo.setNome(dto.getNome());
        grupo.setDescricao(dto.getDescricao());
        grupo.setCriadorGrupo(criador.get());

        Grupo salvo = grupoService.criarGrupo(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(converter(salvo));
    }

    @GetMapping
    public ResponseEntity<List<GrupoResponseDTO>> listarTodos() {
        List<GrupoResponseDTO> lista = grupoService.listarTodos().stream()
                .map(this::converter)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> buscarPorId(@PathVariable Long id) {
        return grupoService.buscarPorId(id)
                .map(g -> ResponseEntity.ok(converter(g)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody GrupoUpdateDTO dto) {
        Optional<Grupo> opt = grupoService.buscarPorId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Grupo grupo = opt.get();
        if (dto.getNome() != null) grupo.setNome(dto.getNome());
        if (dto.getDescricao() != null) grupo.setDescricao(dto.getDescricao());
        Grupo atualizado = grupoService.atualizarGrupo(grupo);
        return ResponseEntity.ok(converter(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            grupoService.removerGrupo(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private GrupoResponseDTO converter(Grupo g) {
        GrupoResponseDTO r = new GrupoResponseDTO();
        r.setId(g.getId());
        r.setNome(g.getNome());
        r.setDescricao(g.getDescricao());
        r.setCriadorId(g.getCriadorGrupo() != null ? g.getCriadorGrupo().getId() : null);
        if (g.getMembrosGrupo() != null) {
            r.setMembrosIds(g.getMembrosGrupo().stream().map(Usuario::getId).collect(Collectors.toList()));
        }
        return r;
    }
}


