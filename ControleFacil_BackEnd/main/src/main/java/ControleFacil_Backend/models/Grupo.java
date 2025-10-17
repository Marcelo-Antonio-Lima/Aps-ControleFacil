package ControleFacil_Backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "grupos")
public class Grupo {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do grupo é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Column(name = "descricao", nullable = true)
    private String descricao;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_grupo_id", nullable = false)
    private Usuario criadorGrupo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "grupos_usuarios",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> membrosGrupo;

    // Constructors
    public Grupo() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Grupo(String nome, String descricao, Usuario criadorGrupo, List<Usuario> membrosGrupo) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.criadorGrupo = criadorGrupo;
        this.membrosGrupo = membrosGrupo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getCriadorGrupo() {
        return criadorGrupo;
    }

    public void setCriadorGrupo(Usuario criadorGrupo) {
        this.criadorGrupo = criadorGrupo;
    }

    public List<Usuario> getMembrosGrupo() {
        return membrosGrupo;
    }

    public void setMembrosGrupo(List<Usuario> membrosGrupo) {
        this.membrosGrupo = membrosGrupo;
    }

    // JPA Lifecycle Methods
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
