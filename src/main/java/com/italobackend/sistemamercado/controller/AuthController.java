package com.italobackend.sistemamercado.controller;

import com.italobackend.sistemamercado.dto.request.AuthRequestDTO;
import com.italobackend.sistemamercado.dto.request.UserRequestDTO;
import com.italobackend.sistemamercado.dto.response.AuthResponse;
import com.italobackend.sistemamercado.entity.User;
import com.italobackend.sistemamercado.repository.UserRepository;
import com.italobackend.sistemamercado.security.JwtService;
import com.italobackend.sistemamercado.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(UserService userService, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository) {
        this.userService = userService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public AuthResponse registrar(@RequestBody UserRequestDTO request) {
        User newUser = userService.createUser(request);
        String token = jwtService.generateToken(newUser);

        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails userDetails = userRepository.findByUsername(request.username()).orElseThrow();
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }

}
