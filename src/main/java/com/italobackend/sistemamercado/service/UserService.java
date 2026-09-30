package com.italobackend.sistemamercado.service;

import com.italobackend.sistemamercado.dto.request.UserRequestDTO;
import com.italobackend.sistemamercado.entity.User;
import com.italobackend.sistemamercado.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BadCredentialsException("Usuário ou senha inválidos."));
    }

    public User createUser(UserRequestDTO dto) {
        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Esse usuário já está cadastrado.");
        }
        User user = new User(
                dto.name(),
                dto.username(),
                passwordEncoder.encode(dto.password())
        );

        return userRepository.save(user);
    }
}