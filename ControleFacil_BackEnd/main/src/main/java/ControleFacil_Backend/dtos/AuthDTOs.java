package ControleFacil_Backend.dtos;

public class AuthDTOs {
    public static class LoginRequest {
        private String usernameOrEmail;
        private String senha;
        public String getUsernameOrEmail() { return usernameOrEmail; }
        public void setUsernameOrEmail(String usernameOrEmail) { this.usernameOrEmail = usernameOrEmail; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }

    public static class LoginResponse {
        private String token;
        private String tokenType = "Bearer";
        private long expiresIn;
        private Long userId;
        private String username;
        private String email;
        public LoginResponse() {}
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }
        public long getExpiresIn() { return expiresIn; }
        public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
