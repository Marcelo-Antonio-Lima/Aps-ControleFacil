package ControleFacil_Backend.services.mensagens;

import ControleFacil_Backend.models.mensagens.Mensagem;
import ControleFacil_Backend.models.mensagens.Chat;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.repositories.mensagens.MensagemRepository;
import ControleFacil_Backend.repositories.mensagens.ChatRepository;
import ControleFacil_Backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Mensagem criarMensagem(Mensagem mensagem) {
        if (mensagem.getChat() == null || mensagem.getChat().getId() == null) {
            throw new IllegalArgumentException("Chat é obrigatório");
        }
        if (mensagem.getUsuario() == null || mensagem.getUsuario().getId() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        return mensagemRepository.save(mensagem);
    }

    @Transactional(readOnly = true)
    public Optional<Mensagem> buscarPorId(Long id) {
        return mensagemRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Mensagem> listarPorChat(Long chatId) {
        return mensagemRepository.findByChat_Id(chatId);
    }

    @Transactional(readOnly = true)
    public List<Mensagem> listarPorUsuario(Long usuarioId) {
        return mensagemRepository.findByUsuario_Id(usuarioId);
    }
}


