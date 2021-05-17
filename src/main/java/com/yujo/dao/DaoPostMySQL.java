package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Post;
import com.yujo.model.User;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

@Repository
public class DaoPostMySQL extends BasicDao implements IDaoPost {

	
	private static final String SELECT_USERS = "SELECT * FROM users";
	private static final String UPDATE_POST = "UPDATE posts SET content = ?";
	private static final String DELETE_POST = "DELETE FROM posts";
	private static final String INSERT_INTO_POSTS = "INSERT INTO posts (id_user, content) values (?,?)";
	private static final String DESC = " ORDER BY content_time DESC";
	private static final String WHERE_ID = " WHERE id=?";
	private static final String SELECT_POSTS = "SELECT * FROM posts";

	public DaoPostMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.psw}") String password) {
		super(dbAddress, user, password);
	}

    
    ////    @GetMapping("/{fileName}")
    ////	public String percorsoFile(@Value("${file.upload}") String uploadDir, @PathVariable String fileName) {
    ////		System.out.println((uploadDir + "/" + fileName));
    ////		return uploadDir + fileName;
    ////	}
	
	@Override
	public List<Post> posts(String uploadDir) {
		List<Post> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll(SELECT_POSTS+DESC);
		for (Map<String, String> map : maps) {
			Post p = setUserInPost(map);
			p.setImage(uploadDir + p.getImage()); 
			ris.add(p);
		}
		return ris;
	}

	private Post setUserInPost(Map<String, String> map) {
		int id_user = Integer.parseInt(map.get("id_user"));
		User u = IMappable.fromMap(User.class, getOne(SELECT_USERS + WHERE_ID, id_user));
		Post p = IMappable.fromMap(Post.class, map);
		p.setUser(u);
		return p;
	}

	@Override
	public Post post(int id) {
		Map<String,String> map = getOne(SELECT_POSTS +WHERE_ID, id);
		return setUserInPost(map);
	}

	@Override
	public boolean add(Post p) {
		return executeAndIsModified(INSERT_INTO_POSTS, p.getUser().getId() ,p.getContent());
	}

	@Override
	public boolean delete(int id) {
		return executeAndIsModified(DELETE_POST+ WHERE_ID, id);
	}

	@Override
	public boolean update(Post p) {
		return executeAndIsModified(UPDATE_POST + WHERE_ID, p.getContent(), p.getId());
	}



}
