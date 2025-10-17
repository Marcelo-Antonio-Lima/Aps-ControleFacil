package ControleFacil_Backend.repositories.mensagens;

import ControleFacil_Backend.models.mensagens.Mensagem;
import ControleFacil_Backend.models.mensagens.Chat;
import ControleFacil_Backend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByChat(Chat chat);
    List<Mensagem> findByChat_Id(Long chatId);
    List<Mensagem> findByUsuario(Usuario usuario);
    List<Mensagem> findByUsuario_Id(Long usuarioId);
}


