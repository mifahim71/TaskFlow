package com.example.taskflow.controller;

import com.example.taskflow.dto.RegisterDto;
import com.example.taskflow.service.TokenService;
import com.example.taskflow.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final TokenService tokenService;

    @GetMapping
    public String hey(){
        return "working";
    }


    @PostMapping
    public ResponseEntity<String> registration(@RequestBody RegisterDto registerDto){
        try {
            userService.registerUser(registerDto);
            return ResponseEntity.ok("check Email to enable your account");
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam(name = "token") String token){
        try{

            String message = tokenService.verifyToken(token);
            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
