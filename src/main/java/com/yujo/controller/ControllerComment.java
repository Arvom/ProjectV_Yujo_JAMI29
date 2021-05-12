package com.yujo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yujo.dao.IDaoComment;
import com.yujo.model.Comment;

@RestController
@RequestMapping("/comment")
public class ControllerComment {
	
	@Autowired
	IDaoComment dao;
	
	@GetMapping("/{id_post}")
	public List<Comment> get(@PathVariable int id_post){
		return dao.comments(id_post);
	}

	@PostMapping
	public boolean add(@RequestBody Comment comment){
		return dao.add(comment);
	}

	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable int id){
		return dao.delete(id);
	}

	@PutMapping
	public boolean update(@RequestBody Comment comment){
		return dao.update(comment);
	}

}
