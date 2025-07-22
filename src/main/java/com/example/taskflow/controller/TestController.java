package com.example.taskflow.controller;

import com.example.taskflow.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class TestController {

    private UserRepo userRepo;

    @GetMapping
    public String msg(){
        return "Your account is verified";
    }

    @DeleteMapping
    public void delete(){
        userRepo.deleteById(5L);
    }
}
