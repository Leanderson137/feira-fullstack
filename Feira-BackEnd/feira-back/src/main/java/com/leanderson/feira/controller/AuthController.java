package com.leanderson.feira.controller;

import com.leanderson.feira.dto.LoginRequest;
import com.leanderson.feira.dto.LoginResponse;
import com.leanderson.feira.dto.UsuarioRequest;
import com.leanderson.feira.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://feira-fullstack.vercel.app"
})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody UsuarioRequest request) {
        authService.cadastrar(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}