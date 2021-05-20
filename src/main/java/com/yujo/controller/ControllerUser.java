package com.yujo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yujo.dao.IDaoUser;
import com.yujo.model.User;

@RestController
@RequestMapping("/user")
public class ControllerUser {
	
	@Autowired
    private IDaoUser dao;

    @GetMapping
    public List<User>get(){
        return dao.users();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable int id){
        return dao.user(id);
    }

    @GetMapping("email/{email}")
    public User getOne(@PathVariable String email){
        return dao.findByEmail( email );
    }

    @PostMapping()
    public boolean post(@RequestBody User u) {
        return dao.add(u);
    }

    @PutMapping()
    public boolean put(@RequestBody User u) {
        return dao.update(u);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        return dao.delete(id);
    }

    @GetMapping("checkEmail/{email}")
    public boolean checkEmail(@PathVariable String  email){
        return dao.findByEmail( email ) == null;
    }

}
