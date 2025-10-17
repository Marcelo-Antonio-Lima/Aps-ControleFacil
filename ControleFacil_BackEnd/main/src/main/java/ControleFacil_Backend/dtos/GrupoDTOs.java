package ControleFacil_Backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class GrupoDTOs {

    public static class GrupoCreateDTO {
        @NotBlank
        private String nome;
        private String descricao;
        @NotNull
        private Long criadorId;
        private List<Long> membrosIds;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public Long getCriadorId() { return criadorId; }
        public void setCriadorId(Long criadorId) { this.criadorId = criadorId; }
        public List<Long> getMembrosIds() { return membrosIds; }
        public void setMembrosIds(List<Long> membrosIds) { this.membrosIds = membrosIds; }
    }

    public static class GrupoUpdateDTO {
        private String nome;
        private String descricao;

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
    }

    public static class GrupoResponseDTO {
        private Long id;
        private String nome;
        private String descricao;
        private Long criadorId;
        private List<Long> membrosIds;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public Long getCriadorId() { return criadorId; }
        public void setCriadorId(Long criadorId) { this.criadorId = criadorId; }
        public List<Long> getMembrosIds() { return membrosIds; }
        public void setMembrosIds(List<Long> membrosIds) { this.membrosIds = membrosIds; }
    }
}


