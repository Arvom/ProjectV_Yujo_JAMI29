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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yujo.dao.IDaoPost;
import com.yujo.model.Post;
import com.yujo.service.FileStorageService;

@RestController
@RequestMapping("/post")
public class ControllerPost {


    @Autowired
    private FileStorageService fs;
    @Autowired
    private IDaoPost dao;

    @GetMapping()
    public List<Post> get() {
        return dao.posts();
    }

    
    @GetMapping("/{id_user}")
    public List <Post> get(@PathVariable int id_user ) {
        return dao.posts(id_user);
    }


    @PostMapping("/upload")
    public String uploadFile( @RequestParam MultipartFile file ) {
        if ( !file.isEmpty() ) {
            return fs.salvaFile( file );
        }
        return null;
    }

    @PostMapping()
    public boolean add( @RequestBody Post p ) {
        return dao.add( p );
    }

    @DeleteMapping("/{id}")
    public boolean delete( @PathVariable int id ) {
        return dao.delete( id );
    }

    @PutMapping
    public boolean update( @RequestBody Post p ) {
        return dao.update( p );
    }
}
