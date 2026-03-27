package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.LoginRequest;
import edu.eci.dosw.DOSW_Library.controller.dto.LoginResponse;
import edu.eci.dosw.DOSW_Library.security.JwtService;
import edu.eci.dosw.DOSW_Library.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserDetailsServiceImpl userDetailsService,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        log.info("Intentando login para usuario: {}", request.getUsername());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        log.info("Usuario encontrado: {}", userDetails.getUsername());
        log.info("Password en BD (primeros 10 chars): {}", userDetails.getPassword().substring(0, 10));
        boolean matches = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());
        log.info("Password matches: {}", matches);
        if (!matches) {
            throw new BadCredentialsException("Credenciales inválidas.");
        }
        String token = jwtService.generateToken(userDetails);
        log.info("Token generado exitosamente");
        return new LoginResponse(token);
    }
}