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
import com.yujo.model.Option;
import com.yujo.dao.IDaoOption;

@RestController
@RequestMapping("/option")
public class ControllerOption {
	
	@Autowired
	IDaoOption dao;
	
	@GetMapping("/{id_post}")
	public List<Option> get(@PathVariable int id_post){
		return dao.options(id_post);
	}
	
	
	@PostMapping()
	public void post(@RequestBody Option o) {
	  dao.add(o);
	}
	
	@PostMapping("/{id}")
	public void post(@RequestBody Option o, @PathVariable int id) {
	  dao.add(o, id);
	}
	
	

	@PutMapping()
	public void put(@RequestBody Option o) {
		dao.update(o);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
	  dao.delete(id);
	}	
}
