package ControleFacil_Backend.controllers.mensagens;

import ControleFacil_Backend.dtos.ChatDTOs.ChatCreateDTO;
import ControleFacil_Backend.dtos.ChatDTOs.ChatResponseDTO;
import ControleFacil_Backend.models.mensagens.Chat;
import ControleFacil_Backend.services.mensagens.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponseDTO> criar(@Valid @RequestBody ChatCreateDTO dto) {
        Chat chat = chatService.criarChatParaGrupo(dto.getGrupoId());
        ChatResponseDTO r = new ChatResponseDTO();
        r.setId(chat.getId());
        r.setGrupoId(chat.getGrupo() != null ? chat.getGrupo().getId() : null);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<ChatResponseDTO> buscarPorGrupo(@PathVariable Long grupoId) {
        Optional<Chat> opt = chatService.buscarPorGrupo(grupoId);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Chat chat = opt.get();
        ChatResponseDTO r = new ChatResponseDTO();
        r.setId(chat.getId());
        r.setGrupoId(chat.getGrupo() != null ? chat.getGrupo().getId() : null);
        return ResponseEntity.ok(r);
    }
}


