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

	private static final String DELETE_USER = "DELETE FROM users WHERE id= ?";
	private static final String UPDATE_USERS = "UPDATE users SET tax_code=?, name=?, surname=?, phone=?, address=?";
	private static final String INSERT_INTO_USERS = "INSERT INTO users (tax_code, name, surname, phone, address) VALUES (?,?,?,?,?)";
	private static final String WHERE_ID = " WHERE id = ?";
	private static final String SELECT_USERS = "SELECT * FROM users";

	public DaoUserMySQL(
			@Value("${db.address}")String dbAddress,
			@Value("${db.user}") String user,
			@Value("${db.psw}") String password) {
		super(dbAddress, user, password);
	}

	@Override
	public List<User> users() {
		List<User> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll(SELECT_USERS);
		for (Map<String, String> map : maps) {
			ris.add(IMappable.fromMap(User.class, map));
		}
		return ris;
	}

	@Override
	public User user(int id) {
		Map<String,String> map = getOne(SELECT_USERS+ WHERE_ID, id);
		return IMappable.fromMap(User.class, map);
	}

	@Override
	public String add(User u) {
		int ris = insertAndGetId(INSERT_INTO_USERS, u.getTax_code(),
				u.getName(),
				u.getSurname(),
				u.getPhone(),
				u.getAddress());
		return "{ \"id\" : \"" + ris + "\"}";
	}

	@Override
	public boolean delete(int id) {
		return executeAndIsModified(DELETE_USER, id);
	}

	@Override
	public boolean update(User u) {
		return executeAndIsModified(UPDATE_USERS + WHERE_ID, u.getTax_code(),
				u.getName(),
				u.getSurname(),
				u.getPhone(),
				u.getAddress(),
				u.getId());
	}
}
