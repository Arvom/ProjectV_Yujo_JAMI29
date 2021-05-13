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

	
	private static final String UPDATE_POST = "UPDATE posts SET content = ?";
	private static final String DELETE_POST = "DELETE FROM posts";
	private static final String INSERT_INTO_POSTS = "INSERT INTO posts (id_user, content) values (?,?)";
	private static final String DESC = " ORDER BY content_time DESC";
	private static final String WHERE_POST_ID = " WHERE posts.id=?";
	private static final String SELECT__POSTS = "SELECT name, surname, posts.* FROM posts INNER JOIN users ON users.id = posts.id_user";

	public DaoPostMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.psw}") String password) {
		super(dbAddress, user, password);
	}

	@Override
	public List<Post> posts() {
		List<Post> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll(SELECT__POSTS+DESC);
		for (Map<String, String> map : maps) {
			ris.add(IMappable.fromMap(Post.class, map));
		}
		return ris;
	}

	@Override
	public Post post(int id) {
		Map<String,String> map = getOne(SELECT__POSTS +WHERE_POST_ID, id);
		return IMappable.fromMap(Post.class, map);
	}

	@Override
	public boolean add(Post p, int id_user) {
		return executeAndIsModified(INSERT_INTO_POSTS, id_user ,p.getContent());
	}

	@Override
	public boolean delete(int id) {
		return executeAndIsModified(DELETE_POST+ WHERE_POST_ID, id);
	}

	@Override
	public boolean update(Post p) {
		return executeAndIsModified(UPDATE_POST + WHERE_POST_ID, p.getContent(), p.getId());
	}

}
