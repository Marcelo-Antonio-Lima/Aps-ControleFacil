package ControleFacil_Backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.time.LocalDateTime;

@Entity
@Table(name = "saldos")
public class Saldo {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0.0", message = "Saldo atual deve ser maior ou igual a 0")
    @Column(name = "saldo_atual", nullable = false, precision = 10, scale = 2)
    private Double saldoAtual;
    
    @DecimalMin(value = "0.0", message = "Saldo mensal deve ser maior ou igual a 0")
    @Column(name = "saldo_mensal", nullable = false, precision = 10, scale = 2)
    private Double saldoMensal;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Constructors
    public Saldo() {
        this.dataCriacao = LocalDateTime.now();
        this.saldoAtual = 0.0;
        this.saldoMensal = 0.0;
    }
   
    public Saldo(Double saldoAtual, Double saldoMensal, Usuario usuario) {
        this();
        this.saldoAtual = saldoAtual != null ? saldoAtual : 0.0;
        this.saldoMensal = saldoMensal != null ? saldoMensal : 0.0;
        this.usuario = usuario;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(Double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public Double getSaldoMensal() {
        return saldoMensal;
    }

    public void setSaldoMensal(Double saldoMensal) {
        this.saldoMensal = saldoMensal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    // JPA Lifecycle Methods
    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (saldoAtual == null) {
            saldoAtual = 0.0;
        }
        if (saldoMensal == null) {
            saldoMensal = 0.0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    // Business Methods
    public void adicionarSaldoAtual(Double valor) {
        if (valor != null && valor > 0) {
            this.saldoAtual += valor;
        }
    }

    public void subtrairSaldoAtual(Double valor) {
        if (valor != null && valor > 0 && this.saldoAtual >= valor) {
            this.saldoAtual -= valor;
        }
    }

    public boolean temSaldoSuficiente(Double valor) {
        return valor != null && this.saldoAtual != null && this.saldoAtual >= valor;
    }

    // Utility Methods
    @Override
    public String toString() {
        return "Saldo{" +
                "id=" + id +
                ", saldoAtual=" + saldoAtual +
                ", saldoMensal=" + saldoMensal +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", usuarioId=" + (usuario != null ? usuario.getId() : null) +
                '}';
    }
}
