package SimulaEnem.controller;

import SimulaEnem.dto.auth.DadosLogin;
import SimulaEnem.dto.auth.DadosTokenJWT;
import SimulaEnem.dto.usuario.DadosCadastroUsuario;
import SimulaEnem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<DadosTokenJWT> register(@RequestBody @Valid DadosCadastroUsuario dados) {
        return ResponseEntity.ok(authService.register(dados));
    }

    @PostMapping("/login")
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosLogin dados) {
        return ResponseEntity.ok(authService.login(dados));
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok("Usuário autenticado: " + authentication.getName());
    }
}
