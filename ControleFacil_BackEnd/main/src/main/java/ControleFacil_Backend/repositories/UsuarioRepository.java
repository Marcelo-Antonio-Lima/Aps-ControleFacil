package ControleFacil_Backend.repositories;

import ControleFacil_Backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca um usuário pelo username
     * @param username o nome de usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByUsername(String username);
    
    /**
     * Busca um usuário pelo email
     * @param email o email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica se existe um usuário com o username fornecido
     * @param username o nome de usuário
     * @return true se existe, false caso contrário
     */
    boolean existsByUsername(String username);
    
    /**
     * Verifica se existe um usuário com o email fornecido
     * @param email o email do usuário
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
}
