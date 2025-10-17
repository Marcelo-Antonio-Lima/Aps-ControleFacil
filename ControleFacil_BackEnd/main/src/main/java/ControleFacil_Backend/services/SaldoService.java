package ControleFacil_Backend.services;

import ControleFacil_Backend.models.Saldo;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.repositories.SaldoRepository;
import ControleFacil_Backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SaldoService {

    @Autowired
    private SaldoRepository saldoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cria um novo saldo para um usuário
     * @param saldo o saldo a ser criado
     * @return o saldo criado
     */
    public Saldo criarSaldo(Saldo saldo) {
        return saldoRepository.save(saldo);
    }

    /**
     * Busca o saldo de um usuário pelo ID do usuário
     * @param usuarioId o ID do usuário
     * @return Optional contendo o saldo se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Saldo> buscarPorUsuarioId(Long usuarioId) {
        return saldoRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Busca o saldo de um usuário específico
     * @param usuario o usuário
     * @return Optional contendo o saldo se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Saldo> buscarPorUsuario(Usuario usuario) {
        return saldoRepository.findByUsuario(usuario);
    }

    /**
     * Lista todos os saldos
     * @return lista de todos os saldos
     */
    @Transactional(readOnly = true)
    public List<Saldo> listarTodos() {
        return saldoRepository.findAll();
    }

    /**
     * Atualiza o saldo atual de um usuário
     * @param usuarioId o ID do usuário
     * @param novoSaldoAtual o novo valor do saldo atual
     * @return o saldo atualizado
     */
    public Saldo atualizarSaldoAtual(Long usuarioId, Double novoSaldoAtual) {
        Optional<Saldo> saldoOpt = saldoRepository.findByUsuarioId(usuarioId);
        
        if (saldoOpt.isEmpty()) {
            throw new IllegalArgumentException("Saldo não encontrado para o usuário com ID: " + usuarioId);
        }
        
        Saldo saldo = saldoOpt.get();
        saldo.setSaldoAtual(novoSaldoAtual);
        
        return saldoRepository.save(saldo);
    }

    /**
     * Atualiza o saldo mensal de um usuário
     * @param usuarioId o ID do usuário
     * @param novoSaldoMensal o novo valor do saldo mensal
     * @return o saldo atualizado
     */
    public Saldo atualizarSaldoMensal(Long usuarioId, Double novoSaldoMensal) {
        Optional<Saldo> saldoOpt = saldoRepository.findByUsuarioId(usuarioId);
        
        if (saldoOpt.isEmpty()) {
            throw new IllegalArgumentException("Saldo não encontrado para o usuário com ID: " + usuarioId);
        }
        
        Saldo saldo = saldoOpt.get();
        saldo.setSaldoMensal(novoSaldoMensal);
        
        return saldoRepository.save(saldo);
    }

    /**
     * Adiciona valor ao saldo atual
     * @param usuarioId o ID do usuário
     * @param valor o valor a ser adicionado
     * @return o saldo atualizado
     */
    public Saldo adicionarAoSaldoAtual(Long usuarioId, Double valor) {
        Optional<Saldo> saldoOpt = saldoRepository.findByUsuarioId(usuarioId);
        
        if (saldoOpt.isEmpty()) {
            throw new IllegalArgumentException("Saldo não encontrado para o usuário com ID: " + usuarioId);
        }
        
        Saldo saldo = saldoOpt.get();
        Double saldoAtual = saldo.getSaldoAtual() != null ? saldo.getSaldoAtual() : 0.0;
        saldo.setSaldoAtual(saldoAtual + valor);
        
        return saldoRepository.save(saldo);
    }

    /**
     * Subtrai valor do saldo atual
     * @param usuarioId o ID do usuário
     * @param valor o valor a ser subtraído
     * @return o saldo atualizado
     */
    public Saldo subtrairDoSaldoAtual(Long usuarioId, Double valor) {
        Optional<Saldo> saldoOpt = saldoRepository.findByUsuarioId(usuarioId);
        
        if (saldoOpt.isEmpty()) {
            throw new IllegalArgumentException("Saldo não encontrado para o usuário com ID: " + usuarioId);
        }
        
        Saldo saldo = saldoOpt.get();
        Double saldoAtual = saldo.getSaldoAtual() != null ? saldo.getSaldoAtual() : 0.0;
        
        if (saldoAtual < valor) {
            throw new IllegalArgumentException("Saldo insuficiente. Saldo atual: " + saldoAtual + ", Valor solicitado: " + valor);
        }
        
        saldo.setSaldoAtual(saldoAtual - valor);
        
        return saldoRepository.save(saldo);
    }

    /**
     * Remove um saldo pelo ID
     * @param id o ID do saldo a ser removido
     */
    public void removerSaldo(Long id) {
        if (!saldoRepository.existsById(id)) {
            throw new IllegalArgumentException("Saldo não encontrado com ID: " + id);
        }
        
        saldoRepository.deleteById(id);
    }
}
