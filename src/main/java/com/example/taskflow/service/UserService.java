package com.example.taskflow.service;

import com.example.taskflow.dto.RegisterDto;
import com.example.taskflow.dto.UserDto;
import com.example.taskflow.entity.User;
import com.example.taskflow.entity.VerificationToken;
import com.example.taskflow.enums.Role;
import com.example.taskflow.mapper.UserMapper;
import com.example.taskflow.repository.TokenRepo;
import com.example.taskflow.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepo tokenRepo;

    private final EmailService emailService;

    private final UserMapper userMapper;

    @Transactional
    public void registerUser(RegisterDto registerDto){
        User user = new User();

        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.ADMIN);

        userRepo.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));

        tokenRepo.save(verificationToken);

        String url = "http://localhost:8090/api/auth/verify?token=" + token;
        emailService.sendEmail(user.getEmail(), "Verify your TaskFlow account", "Click Here to verify your account. \n"+url);
    }

    public UserDto findUser(String email) {

        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("user not found"));
        return userMapper.toDto(user);
    }

    public UserDto findById(Long id) {

        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        return userMapper.toDto(user);
    }

    public List<UserDto> findAllUsers() {

        List<User> users = userRepo.findAll();

        return users.stream().map(userMapper::toDto).toList();
    }
}
