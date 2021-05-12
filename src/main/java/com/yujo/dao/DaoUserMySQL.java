package com.yujo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.yujo.model.User;
import com.yujo.util.BasicDao;

import com.yujo.util.IMappable;

@Repository
public class DaoUserMySQL extends BasicDao implements IDaoUser{

	public DaoUserMySQL(
			@Value("${db.address}")String dbAddress,
			@Value("${db.user}") String user,
			@Value("${db.psw}") String password) {
		super(dbAddress, user, password);
	}

	@Override
	public List<User> users() {
		List<User> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll("SELECT * FROM users");
		for (Map<String, String> map : maps) {
			ris.add(IMappable.fromMap(User.class, map));
		}
		return ris;
	}

	@Override
	public User user(int id) {
		Map<String,String> map = getOne("SELECT * FROM users WHERE id = ?", id);
		if(map!= null){
			return IMappable.fromMap(User.class, map);
		}else{
			return null;
		}

	}

	@Override
	public boolean add(User u) {
		return executeAndIsModified("INSERT INTO users (tax_code, name, surname, phone, address) VALUES (?,?,?,?,?)", u.getTax_code(),
				u.getName(),
				u.getSurname(),
				u.getPhone(),
				u.getPhone(),
				u.getAddress());
	}

	@Override
	public boolean delete(int id) {
		execute("DELETE FROM posts WHERE id_user = ?", id);
		return executeAndIsModified("DELETE FROM users WHERE id= ?", id);
	}

	@Override
	public boolean update(User u) {
		if(u!= null){
			return executeAndIsModified("UPDATE users SET tax_code, name=?, surname=?, phone=?, address=? WHERE id = ?", u.getTax_code(),
					u.getName(),
					u.getSurname(),
					u.getPhone(),
					u.getPhone(),
					u.getAddress());
		}else{
			return false;
		}

	}
	
	
}
