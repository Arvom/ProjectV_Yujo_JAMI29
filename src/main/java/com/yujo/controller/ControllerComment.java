package com.yujo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yujo.dao.IDaoComment;
import com.yujo.model.Comment;

@RestController
@RequestMapping("/comment")
public class ControllerComment {
	
	@Autowired
	IDaoComment dao;
	
	@GetMapping("/{id_post}")
	public List<Comment> get(int id_post){
		return dao.comments(id_post);
	}


}
