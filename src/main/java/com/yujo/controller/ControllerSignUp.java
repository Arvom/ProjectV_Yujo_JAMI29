package com.yujo.controller;


import com.yujo.model.User;
import com.yujo.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/signup")
public class ControllerSignUp {

    @Autowired
    private AuthService authService;

    @PostMapping
    public boolean signup (@RequestBody User u){
        return  authService.signup(u);
    }


}
