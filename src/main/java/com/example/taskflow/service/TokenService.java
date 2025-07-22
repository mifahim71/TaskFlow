package com.example.taskflow.service;

import com.example.taskflow.entity.User;
import com.example.taskflow.entity.VerificationToken;
import com.example.taskflow.repository.TokenRepo;
import com.example.taskflow.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepo tokenRepo;

    private final UserRepo userRepo;

    public String verifyToken(String token){
        VerificationToken verificationToken = tokenRepo.findByToken(token).orElseThrow(() -> new RuntimeException("Token unavailable"));

        if(verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            tokenRepo.delete(verificationToken);
            return "The Token Has expired";
        }

        User user = verificationToken.getUser();
        if (user.isVerified()) {
            return "Account already verified.";
        }

        user.setVerified(true);
        userRepo.save(user);
        tokenRepo.delete(verificationToken);

        return "Account Verified Successfully";
    }
}
