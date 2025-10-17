package ControleFacil_Backend.controllers;

import ControleFacil_Backend.dtos.SaldoCreateDTO;
import ControleFacil_Backend.dtos.SaldoResponseDTO;
import ControleFacil_Backend.dtos.SaldoUpdateDTO;
import ControleFacil_Backend.models.Saldo;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.services.SaldoService;
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
@RequestMapping("/api/saldos")
@CrossOrigin(origins = "*")
public class SaldoController {

    @Autowired
    private SaldoService saldoService;
    
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Cria um novo saldo
     */
    @PostMapping
    public ResponseEntity<SaldoResponseDTO> criarSaldo(@Valid @RequestBody SaldoCreateDTO saldoCreateDTO) {
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(saldoCreateDTO.getUsuarioId());
            
            if (usuario.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Saldo saldo = new Saldo();
            saldo.setSaldoAtual(saldoCreateDTO.getSaldoAtual());
            saldo.setSaldoMensal(saldoCreateDTO.getSaldoMensal());
            saldo.setUsuario(usuario.get());
            
            Saldo saldoSalvo = saldoService.criarSaldo(saldo);
            SaldoResponseDTO responseDTO = converterParaResponseDTO(saldoSalvo);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Busca o saldo de um usuário pelo ID do usuário
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<SaldoResponseDTO> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        Optional<Saldo> saldo = saldoService.buscarPorUsuarioId(usuarioId);
        
        if (saldo.isPresent()) {
            SaldoResponseDTO responseDTO = converterParaResponseDTO(saldo.get());
            return ResponseEntity.ok(responseDTO);
        }
        
        return ResponseEntity.notFound().build();
    }

    /**
     * Lista todos os saldos
     */
    @GetMapping
    public ResponseEntity<List<SaldoResponseDTO>> listarTodos() {
        List<Saldo> saldos = saldoService.listarTodos();
        List<SaldoResponseDTO> responseDTOs = saldos.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Atualiza o saldo atual de um usuário
     */
    @PutMapping("/usuario/{usuarioId}/saldo-atual")
    public ResponseEntity<SaldoResponseDTO> atualizarSaldoAtual(
            @PathVariable Long usuarioId, 
            @RequestBody Double novoSaldoAtual) {
        try {
            Saldo saldoAtualizado = saldoService.atualizarSaldoAtual(usuarioId, novoSaldoAtual);
            SaldoResponseDTO responseDTO = converterParaResponseDTO(saldoAtualizado);
            
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza o saldo mensal de um usuário
     */
    @PutMapping("/usuario/{usuarioId}/saldo-mensal")
    public ResponseEntity<SaldoResponseDTO> atualizarSaldoMensal(
            @PathVariable Long usuarioId, 
            @RequestBody Double novoSaldoMensal) {
        try {
            Saldo saldoAtualizado = saldoService.atualizarSaldoMensal(usuarioId, novoSaldoMensal);
            SaldoResponseDTO responseDTO = converterParaResponseDTO(saldoAtualizado);
            
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Adiciona valor ao saldo atual
     */
    @PostMapping("/usuario/{usuarioId}/adicionar")
    public ResponseEntity<SaldoResponseDTO> adicionarAoSaldoAtual(
            @PathVariable Long usuarioId, 
            @RequestBody Double valor) {
        try {
            Saldo saldoAtualizado = saldoService.adicionarAoSaldoAtual(usuarioId, valor);
            SaldoResponseDTO responseDTO = converterParaResponseDTO(saldoAtualizado);
            
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Subtrai valor do saldo atual
     */
    @PostMapping("/usuario/{usuarioId}/subtrair")
    public ResponseEntity<SaldoResponseDTO> subtrairDoSaldoAtual(
            @PathVariable Long usuarioId, 
            @RequestBody Double valor) {
        try {
            Saldo saldoAtualizado = saldoService.subtrairDoSaldoAtual(usuarioId, valor);
            SaldoResponseDTO responseDTO = converterParaResponseDTO(saldoAtualizado);
            
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Remove um saldo pelo ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerSaldo(@PathVariable Long id) {
        try {
            saldoService.removerSaldo(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Converte Saldo para SaldoResponseDTO
     */
    private SaldoResponseDTO converterParaResponseDTO(Saldo saldo) {
        return new SaldoResponseDTO(
            saldo.getId(),
            saldo.getSaldoAtual(),
            saldo.getSaldoMensal(),
            saldo.getUsuario() != null ? saldo.getUsuario().getId() : null
        );
    }
}
