package ControleFacil_Backend.dtos;

/**
 * DTO para resposta de usuário (sem informações sensíveis)
 */
public class UsuarioResponseDTO {

    private Long id;
    private String username;
    private String email;
    private SaldoResponseDTO saldo;

    // Constructors
    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String username, String email, SaldoResponseDTO saldo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.saldo = saldo;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SaldoResponseDTO getSaldo() {
        return saldo;
    }

    public void setSaldo(SaldoResponseDTO saldo) {
        this.saldo = saldo;
    }
}
