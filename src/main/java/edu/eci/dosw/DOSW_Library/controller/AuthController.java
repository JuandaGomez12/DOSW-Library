package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.LoginRequest;
import edu.eci.dosw.DOSW_Library.controller.dto.LoginResponse;
import edu.eci.dosw.DOSW_Library.core.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/hash")
    public ResponseEntity<String> hash(@RequestParam String password) {
        return ResponseEntity.ok(new BCryptPasswordEncoder().encode(password));
    }
}