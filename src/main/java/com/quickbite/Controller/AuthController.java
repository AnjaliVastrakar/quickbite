package com.quickbite.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickbite.DTO.LoginRequestDTO;
import com.quickbite.DTO.LoginResponseDTO;
import com.quickbite.Entity.User;
import com.quickbite.Security.JwtService;
import com.quickbite.Service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        User user = authService.login(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
        );

        String token = authService.generateToken(user);

        LoginResponseDTO response = new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                token
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(
            @RequestBody java.util.Map<String, String> body) {

        String token = body.getOrDefault("token", "");

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(401).body(
                    java.util.Map.of("valid", false));
        }

        return ResponseEntity.ok(java.util.Map.of(
                "valid", true,
                "email", jwtService.extractEmail(token),
                "role", jwtService.extractRole(token)));
    }
}