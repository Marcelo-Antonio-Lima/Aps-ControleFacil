package ControleFacil_Backend.repositories.mensagens;

import ControleFacil_Backend.models.mensagens.Chat;
import ControleFacil_Backend.models.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByGrupo(Grupo grupo);
    Optional<Chat> findByGrupo_Id(Long grupoId);
}


