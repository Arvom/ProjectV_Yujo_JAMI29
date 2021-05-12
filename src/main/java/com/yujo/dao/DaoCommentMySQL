package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Comment;
import com.yujo.model.Post;
import com.yujo.model.User;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

@Repository
public class DaoCommentMySQL extends BasicDao implements IDaoComment{

	public DaoCommentMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.password}") String password){
		super(dbAddress, user, password);
	}

	private Comment setStuffInComment(int id_post, int id_user, Map<String, String> map) {
		Post p = IMappable.fromMap(Post.class, getOne("SELECT * FROM posts WHERE id = ?", id_post));
		User u = IMappable.fromMap(User.class, getOne("SELECT * FROM users WHERE id = ?", id_user));
		Comment c = IMappable.fromMap(Comment.class, map);
		c.setPost(p);
		c.setUser(u);
		return c;
	}
	
	@Override
	public List<Comment> comments(int id_post) {
		List<Comment> res = new ArrayList<>();
		List<Map<String, String>> maps = getAll("SELECT * FROM comments WHERE id_post = ?", id_post);
		for (Map<String, String> map : maps) {
			int id_user = Integer.parseInt(map.get("id_user"));
			res.add(setStuffInComment(id_post, id_user, map));
		}
		return res;
	}

	@Override
	public boolean add(Comment c) {
		return executeAndIsModified("INSERT INTO comments (id_user, id_post, content) VALUES (?,?,?)",c.getUser().getId(), c.getPost().getId(), c.getContent());
	}

	@Override
	public boolean delete(int id) {
		
		return false;
	}

	@Override
	public boolean update(Comment c) {
		
		return false;
	}

}
