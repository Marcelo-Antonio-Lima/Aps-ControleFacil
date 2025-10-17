package ControleFacil_Backend.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para criação de saldo
 */
public class SaldoCreateDTO {

    @DecimalMin(value = "0.0", message = "Saldo atual deve ser maior ou igual a 0")
    private Double saldoAtual;

    @DecimalMin(value = "0.0", message = "Saldo mensal deve ser maior ou igual a 0")
    private Double saldoMensal;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    // Constructors
    public SaldoCreateDTO() {
    }

    public SaldoCreateDTO(Double saldoAtual, Double saldoMensal, Long usuarioId) {
        this.saldoAtual = saldoAtual;
        this.saldoMensal = saldoMensal;
        this.usuarioId = usuarioId;
    }

    // Getters and Setters
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
