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
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private SaldoRepository saldoRepository;

    /**
     * Cria um novo usuário
     * @param usuario o usuário a ser criado
     * @return o usuário criado
     * @throws IllegalArgumentException se username ou email já existirem
     */
    public Usuario criarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Username já existe: " + usuario.getUsername());
        }
        
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já existe: " + usuario.getEmail());
        }
        
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        
        // Criar saldo inicial para o usuário
        Saldo saldoInicial = new Saldo(0.0, 0.0, usuarioSalvo);
        saldoRepository.save(saldoInicial);
        usuarioSalvo.setSaldo(saldoInicial);
        
        return usuarioSalvo;
    }

    /**
     * Busca um usuário pelo ID
     * @param id o ID do usuário
     * @return Optional contendo o usuário se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca um usuário pelo username
     * @param username o nome de usuário
     * @return Optional contendo o usuário se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Busca um usuário pelo email
     * @param email o email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Lista todos os usuários
     * @return lista de todos os usuários
     */
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Atualiza um usuário existente
     * @param usuario o usuário com os dados atualizados
     * @return o usuário atualizado
     */
    public Usuario atualizarUsuario(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + usuario.getId());
        }
        
        return usuarioRepository.save(usuario);
    }

    /**
     * Remove um usuário pelo ID
     * @param id o ID do usuário a ser removido
     */
    public void removerUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        
        usuarioRepository.deleteById(id);
    }

    /**
     * Verifica se um usuário existe pelo ID
     * @param id o ID do usuário
     * @return true se existe, false caso contrário
     */
    @Transactional(readOnly = true)
    public boolean existeUsuario(Long id) {
        return usuarioRepository.existsById(id);
    }
}
