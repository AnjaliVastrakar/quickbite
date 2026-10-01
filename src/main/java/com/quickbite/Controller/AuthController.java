package com.quickbite.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickbite.DTO.LoginRequestDTO;
import com.quickbite.DTO.LoginResponseDTO;
import com.quickbite.Entity.User;
import com.quickbite.Service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}