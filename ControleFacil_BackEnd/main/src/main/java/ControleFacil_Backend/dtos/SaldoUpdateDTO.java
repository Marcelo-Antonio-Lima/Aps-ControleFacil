package ControleFacil_Backend.dtos;

import jakarta.validation.constraints.DecimalMin;

/**
 * DTO para atualização de saldo
 */
public class SaldoUpdateDTO {

    @DecimalMin(value = "0.0", message = "Saldo atual deve ser maior ou igual a 0")
    private Double saldoAtual;

    @DecimalMin(value = "0.0", message = "Saldo mensal deve ser maior ou igual a 0")
    private Double saldoMensal;

    // Constructors
    public SaldoUpdateDTO() {
    }

    public SaldoUpdateDTO(Double saldoAtual, Double saldoMensal) {
        this.saldoAtual = saldoAtual;
        this.saldoMensal = saldoMensal;
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
}
