package ControleFacil_Backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO para atualização de usuário
 */
public class UsuarioUpdateDTO {

    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    private String username;

    @Email(message = "Email deve ter um formato válido")
    private String email;

    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;

    // Constructors
    public UsuarioUpdateDTO() {
    }

    public UsuarioUpdateDTO(String username, String email, String senha) {
        this.username = username;
        this.email = email;
        this.senha = senha;
    }

    // Getters and Setters
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
