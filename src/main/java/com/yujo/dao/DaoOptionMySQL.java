package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.Option;
import com.yujo.model.Post;
import com.yujo.model.User;
import com.yujo.util.BasicDao;
import com.yujo.util.IMappable;

@Repository
public class DaoOptionMySQL extends BasicDao implements IDaoOption {

	private static final String SELECT_USER = "SELECT * FROM users";
	private static final String UPDATE_OPTION_ID_USERS = "UPDATE options SET id_users = ?";
	private static final String UPDATE_OPTIONS_CONTENT = "UPDATE options SET content=?, id_users=''";
	private static final String DELETE_OPTIONS = "DELETE FROM options";
	private static final String INSERT_INTO_OPTIONS = "INSERT INTO options(id_post, content, id_user, id_users) VALUES (?,?,?,?)";
	private static final String WHERE_ID = " WHERE id = ?";
	private static final String SELECT_POST = "SELECT * FROM posts";
	private static final String WHERE_ID_POST = " WHERE id_post = ?";
	private static final String SELECT_OPTIONS = "SELECT * FROM options";

	public DaoOptionMySQL(
			@Value ("${db.address}") String dbAddress,
			@Value ("${db.user}") String user,
			@Value ("${db.psw}") String password) {
		super(dbAddress, user, password);
	}

	@Override
	public List<Option> options(int id_post) {
		List<Option> res = new ArrayList<>();
		List<Map<String,String>> maps = getAll(SELECT_OPTIONS + WHERE_ID_POST, id_post);
		
		for (Map<String, String> map : maps) {
			
			Option option = IMappable.fromMap(Option.class, map);
			
			Map<String,String> post = getOne(SELECT_POST + WHERE_ID, id_post);
			Post p = IMappable.fromMap(Post.class, post);
			option.setPost(p);
			
			int id_user = Integer.parseInt(map.get("id_user"));
			Map <String, String> user = getOne(SELECT_USER + WHERE_ID, id_user);
			User u = IMappable.fromMap(User.class, user);
			option.setUser(u);
			
			res.add(option);
		}
		return res;
	}

	@Override
	public boolean add(Option o) {		
		return executeAndIsModified(INSERT_INTO_OPTIONS, o.getPost().getId(),
				o.getContent(), o.getUser().getId(), o.getUser().getId());
	}

	@Override
	public boolean update(Option o) {
		return executeAndIsModified(UPDATE_OPTIONS_CONTENT+ WHERE_ID, o.getContent(), o.getId());
	}
	
	@Override
	public boolean delete(int id) {
		return executeAndIsModified(DELETE_OPTIONS+ WHERE_ID,id);
	}

	@Override
	public boolean add(Option o, int id) {
		
		String []temp = o.getId_users().split("@");
		boolean hasAlreadyVoted = false;
		
		for (String string : temp) {
			int user_id = Integer.parseInt(string);
			if (id == user_id) {
				hasAlreadyVoted = true;
			}
		}
		
		if(hasAlreadyVoted) {
			return false;
		}
		o.setId_users(o.getId_users()+"@"+id);
		return executeAndIsModified(UPDATE_OPTION_ID_USERS + WHERE_ID,o.getId_users(), o.getId());
	}
}
