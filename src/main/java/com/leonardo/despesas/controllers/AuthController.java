package com.leonardo.despesas.controllers;

import com.leonardo.despesas.DTOs.AuthDTO;
import com.leonardo.despesas.DTOs.LoginResponseDTO;
import com.leonardo.despesas.DTOs.responses.ErrorResponse;
import com.leonardo.despesas.models.User;
import com.leonardo.despesas.repositories.UserRepository;
import com.leonardo.despesas.services.TokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.senha());
        try {
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());

            log.info("Usuário logado! " + token);

            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse("Usuário ou senha inválidos."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AuthDTO data) {
        if (this.userRepository.findByUsername(data.username()).isPresent()) return ResponseEntity.badRequest().body(new ErrorResponse("Usuário já está cadastrado."));
        if (data.senha().length() < 8) return ResponseEntity.badRequest().body(new ErrorResponse("Senha muito curta."));

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(data.username(), encryptedPassword);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}

