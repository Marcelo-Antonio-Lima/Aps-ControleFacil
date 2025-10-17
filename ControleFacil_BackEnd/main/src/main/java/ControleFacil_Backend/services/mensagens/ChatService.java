package ControleFacil_Backend.services.mensagens;

import ControleFacil_Backend.models.mensagens.Chat;
import ControleFacil_Backend.models.Grupo;
import ControleFacil_Backend.repositories.mensagens.ChatRepository;
import ControleFacil_Backend.repositories.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public Chat criarChatParaGrupo(Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado"));

        // um chat por grupo
        Optional<Chat> existente = chatRepository.findByGrupo(grupo);
        if (existente.isPresent()) {
            return existente.get();
        }

        Chat chat = new Chat(grupo);
        return chatRepository.save(chat);
    }

    @Transactional(readOnly = true)
    public Optional<Chat> buscarPorGrupo(Long grupoId) {
        return chatRepository.findByGrupo_Id(grupoId);
    }

    @Transactional(readOnly = true)
    public Optional<Chat> buscarPorId(Long chatId) {
        return chatRepository.findById(chatId);
    }
}


