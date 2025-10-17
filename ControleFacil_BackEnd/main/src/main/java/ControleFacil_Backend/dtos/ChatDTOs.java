package ControleFacil_Backend.dtos;

import jakarta.validation.constraints.NotNull;

public class ChatDTOs {
    public static class ChatCreateDTO {
        @NotNull
        private Long grupoId;

        public Long getGrupoId() { return grupoId; }
        public void setGrupoId(Long grupoId) { this.grupoId = grupoId; }
    }

    public static class ChatResponseDTO {
        private Long id;
        private Long grupoId;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getGrupoId() { return grupoId; }
        public void setGrupoId(Long grupoId) { this.grupoId = grupoId; }
    }
}


