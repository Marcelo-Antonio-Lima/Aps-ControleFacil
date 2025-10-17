package ControleFacil_Backend.repositories;

import ControleFacil_Backend.models.Saldo;
import ControleFacil_Backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaldoRepository extends JpaRepository<Saldo, Long> {
    
    /**
     * Busca o saldo de um usuário específico
     * @param usuario o usuário
     * @return Optional contendo o saldo se encontrado
     */
    Optional<Saldo> findByUsuario(Usuario usuario);
    
    /**
     * Busca o saldo pelo ID do usuário
     * @param usuarioId o ID do usuário
     * @return Optional contendo o saldo se encontrado
     */
    Optional<Saldo> findByUsuarioId(Long usuarioId);
    
    /**
     * Verifica se existe saldo para um usuário específico
     * @param usuario o usuário
     * @return true se existe, false caso contrário
     */
    boolean existsByUsuario(Usuario usuario);
}
