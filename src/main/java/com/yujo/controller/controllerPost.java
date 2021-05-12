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

import com.yujo.dao.IDaoPost;
import com.yujo.model.Post;

@RestController
@RequestMapping("/post")
public class controllerPost {

	@Autowired
    private IDaoPost dao;

    @GetMapping()
    public List<Post> get(){
        return dao.posts();
    }

    @GetMapping("/{id}")
    public Post getOne(@PathVariable int id){
        return dao.post(id);
    }

    @PostMapping
    public boolean add(@RequestBody Post p, @PathVariable int id_user){
        return dao.add(p, id_user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id ){
        return dao.delete(id);
    }

    @PutMapping
    public boolean update(@PathVariable String content){
        return dao.update(content); 
    }
}
