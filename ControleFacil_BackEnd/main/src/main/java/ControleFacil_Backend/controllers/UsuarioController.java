package ControleFacil_Backend.controllers;

import ControleFacil_Backend.dtos.UsuarioCreateDTO;
import ControleFacil_Backend.dtos.UsuarioResponseDTO;
import ControleFacil_Backend.dtos.UsuarioUpdateDTO;
import ControleFacil_Backend.models.Usuario;
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
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Cria um novo usuário
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(usuarioCreateDTO.getUsername());
            usuario.setEmail(usuarioCreateDTO.getEmail());
            usuario.setSenha(usuarioCreateDTO.getSenha());

            Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);
            UsuarioResponseDTO responseDTO = converterParaResponseDTO(usuarioSalvo);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Busca um usuário pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        
        if (usuario.isPresent()) {
            UsuarioResponseDTO responseDTO = converterParaResponseDTO(usuario.get());
            return ResponseEntity.ok(responseDTO);
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * Busca um usuário pelo username
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.buscarPorUsername(username);
        
        if (usuario.isPresent()) {
            UsuarioResponseDTO responseDTO = converterParaResponseDTO(usuario.get());
            return ResponseEntity.ok(responseDTO);
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * Lista todos os usuários
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioResponseDTO> responseDTOs = usuarios.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Atualiza um usuário existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @PathVariable Long id, 
            @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarPorId(id);
            
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Usuario usuario = usuarioOpt.get();
            
            if (usuarioUpdateDTO.getUsername() != null) {
                usuario.setUsername(usuarioUpdateDTO.getUsername());
            }
            if (usuarioUpdateDTO.getEmail() != null) {
                usuario.setEmail(usuarioUpdateDTO.getEmail());
            }
            if (usuarioUpdateDTO.getSenha() != null) {
                usuario.setSenha(usuarioUpdateDTO.getSenha());
            }
            
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
            UsuarioResponseDTO responseDTO = converterParaResponseDTO(usuarioAtualizado);
            
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Remove um usuário pelo ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        try {
            usuarioService.removerUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Converte Usuario para UsuarioResponseDTO
     */
    private UsuarioResponseDTO converterParaResponseDTO(Usuario usuario) {
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setId(usuario.getId());
        responseDTO.setUsername(usuario.getUsername());
        responseDTO.setEmail(usuario.getEmail());
        
        if (usuario.getSaldo() != null) {
            responseDTO.setSaldo(new ControleFacil_Backend.dtos.SaldoResponseDTO(
                usuario.getSaldo().getId(),
                usuario.getSaldo().getSaldoAtual(),
                usuario.getSaldo().getSaldoMensal(),
                usuario.getId()
            ));
        }
        
        return responseDTO;
    }
}
