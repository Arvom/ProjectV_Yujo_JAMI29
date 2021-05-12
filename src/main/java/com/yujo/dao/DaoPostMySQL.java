package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Post;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

@Repository
public class DaoPostMySQL extends BasicDao implements IDaoPost {

	
	public DaoPostMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.password}") String password) {
		super(dbAddress, user, password);
	}

	@Override
	public List<Post> posts() {
		List<Post> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll("SELECT name, surname, posts.* FROM posts INNER JOIN users ON users.id = posts.id_user");
		for (Map<String, String> map : maps) {
			ris.add(IMappable.fromMap(Post.class, map));
		}
		return ris;
	}

	@Override
	public Post post(int id) {
		Map<String,String> map = getOne("SELECT name, surname, posts.* FROM posts INNER JOIN users ON users.id = posts.id_user WHERE id=?", id);
		return IMappable.fromMap(Post.class, map);
	}

	@Override
	public boolean add(Post p, int id_user) {
		return executeAndIsModified("INSERT INTO posts (id_user, content) values (?,?)", id_user ,p.getContent());
	}

	@Override
	public boolean delete(int id) {
		return executeAndIsModified("DELETE * FROM posts WHERE id=?", id);
	}

	@Override
	public boolean update(String content) {
		return executeAndIsModified("UPDATE posts SET content = ?", content);
	}

}
