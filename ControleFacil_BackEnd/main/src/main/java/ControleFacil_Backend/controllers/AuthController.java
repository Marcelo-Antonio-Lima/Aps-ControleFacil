package ControleFacil_Backend.controllers;

import ControleFacil_Backend.config.JwtUtil;
import ControleFacil_Backend.dtos.AuthDTOs.LoginRequest;
import ControleFacil_Backend.dtos.AuthDTOs.LoginResponse;
import ControleFacil_Backend.models.Usuario;
import ControleFacil_Backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${app.jwt.expirationMillis:7200000}")
    private long expirationMillis;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request == null || request.getUsernameOrEmail() == null || request.getSenha() == null) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }

        String key = request.getUsernameOrEmail();
        Optional<Usuario> userOpt = key.contains("@") ?
                usuarioService.buscarPorEmail(key) : usuarioService.buscarPorUsername(key);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }
        Usuario user = userOpt.get();
        if (!user.getSenha().equals(request.getSenha())) {
            return ResponseEntity.status(401).body("Senha inválida");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());

        String token = jwtUtil.generateToken(String.valueOf(user.getId()), claims);
        LoginResponse resp = new LoginResponse();
        resp.setToken(token);
        resp.setExpiresIn(expirationMillis);
        resp.setUserId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setEmail(user.getEmail());

        return ResponseEntity.ok(resp);
    }
}
