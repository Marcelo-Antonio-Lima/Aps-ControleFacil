package ControleFacil_Backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MensagemDTOs {
    public static class MensagemCreateDTO {
        @NotBlank
        private String conteudo;
        @NotNull
        private Long usuarioId;
        @NotNull
        private Long chatId;

        public String getConteudo() { return conteudo; }
        public void setConteudo(String conteudo) { this.conteudo = conteudo; }
        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
        public Long getChatId() { return chatId; }
        public void setChatId(Long chatId) { this.chatId = chatId; }
    }

    public static class MensagemResponseDTO {
        private Long id;
        private String conteudo;
        private Long usuarioId;
        private Long chatId;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getConteudo() { return conteudo; }
        public void setConteudo(String conteudo) { this.conteudo = conteudo; }
        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
        public Long getChatId() { return chatId; }
        public void setChatId(Long chatId) { this.chatId = chatId; }
    }
}


