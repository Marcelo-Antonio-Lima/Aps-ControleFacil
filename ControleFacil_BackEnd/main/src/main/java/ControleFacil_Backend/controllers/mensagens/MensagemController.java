package ControleFacil_Backend.controllers.mensagens;

import ControleFacil_Backend.dtos.MensagemDTOs.MensagemCreateDTO;
import ControleFacil_Backend.dtos.MensagemDTOs.MensagemResponseDTO;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.models.mensagens.Chat;
import ControleFacil_Backend.models.mensagens.Mensagem;
import ControleFacil_Backend.services.UsuarioService;
import ControleFacil_Backend.services.mensagens.ChatService;
import ControleFacil_Backend.services.mensagens.MensagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mensagens")
@CrossOrigin(origins = "*")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<MensagemResponseDTO> criar(@Valid @RequestBody MensagemCreateDTO dto) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(dto.getUsuarioId());
        if (usuario.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Chat> chat = chatService.buscarPorId(dto.getChatId());
        if (chat.isEmpty()) return ResponseEntity.badRequest().build();

        Mensagem msg = new Mensagem(dto.getConteudo(), usuario.get(), chat.get());
        Mensagem salva = mensagemService.criarMensagem(msg);
        MensagemResponseDTO r = toResponse(salva);
        return ResponseEntity.status(HttpStatus.CREATED).body(r);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MensagemResponseDTO>> listarPorChat(@PathVariable Long chatId) {
        List<MensagemResponseDTO> lista = mensagemService.listarPorChat(chatId)
                .stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    private MensagemResponseDTO toResponse(Mensagem m) {
        MensagemResponseDTO r = new MensagemResponseDTO();
        r.setId(m.getId());
        r.setConteudo(m.getConteudo());
        r.setUsuarioId(m.getUsuario() != null ? m.getUsuario().getId() : null);
        r.setChatId(m.getChat() != null ? m.getChat().getId() : null);
        return r;
    }
}


