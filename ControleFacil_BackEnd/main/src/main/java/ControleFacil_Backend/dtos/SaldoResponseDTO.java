package ControleFacil_Backend.dtos;

/**
 * DTO para resposta de saldo
 */
public class SaldoResponseDTO {

    private Long id;
    private Double saldoAtual;
    private Double saldoMensal;
    private Long usuarioId;

    // Constructors
    public SaldoResponseDTO() {
    }

    public SaldoResponseDTO(Long id, Double saldoAtual, Double saldoMensal, Long usuarioId) {
        this.id = id;
        this.saldoAtual = saldoAtual;
        this.saldoMensal = saldoMensal;
        this.usuarioId = usuarioId;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
