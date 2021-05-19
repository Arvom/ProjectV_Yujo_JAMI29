package com.yujo.security;


import com.yujo.dao.IDaoUser;
import com.yujo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {

    private final IDaoUser dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(IDaoUser dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = dao.findByEmail(email);

        if(user != null) return user;

        throw new UsernameNotFoundException("Email non trovata");
    }


    public boolean signup (User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return dao.add(user);
    }

    public boolean checkUserId (Authentication authentication, int id){

        User utente =dao.findByEmail(authentication.getName());

        if (utente != null) {
            return (utente.getId() == id);
        } else {
            return false;
        }
    }

    public User findById(int id){
        return dao.user(id);
    }

}
