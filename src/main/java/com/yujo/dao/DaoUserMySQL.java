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

	private static final String WHERE_ROLE = " WHERE role=?";
	private static final String WHERE_EMAIL = " WHERE email=?";
	private static final String DELETE_USER = "DELETE FROM users WHERE id= ?";
	private static final String UPDATE_USERS = "UPDATE users SET tax_code=?, name=?, surname=?, phone=?, address=?, image=?";
	private static final String INSERT_INTO_USERS = "INSERT INTO users (tax_code, name, surname, phone, address, email, password, role) VALUES (?,?,?,?,?,?,?,?)";
	private static final String WHERE_ID = " WHERE id = ?";
	private static final String SELECT_USERS = "SELECT * FROM users";

	public DaoUserMySQL(
			@Value("${db.address}")String dbAddress,
			@Value("${db.user}") String user,
			@Value("${db.psw}") String password) {
		super(dbAddress, user, password);
	}
	
	/**
	 *This method will return a List of users
	 * @return List
	 */
	@Override
	public List<User> users() {
		List<User> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll(SELECT_USERS);
		for (Map<String, String> map : maps) {
			ris.add(IMappable.fromMap(User.class, map));
		}
		return ris;
	}

	/**
	 *This method will a single user find by his id
	 * @param id
	 * @return user
	 */
	@Override
	public User user(int id) {
		Map<String,String> map = getOne(SELECT_USERS+ WHERE_ID, id);
		if(map!= null){
			return IMappable.fromMap(User.class, map);
		}
		return null;
	}

	/**
	 *With this method we can add in our yujo db a new row and create a new user obj
	 * @param u
	 * @return boolean
	 */
	@Override
	public boolean add(User u) {
		return executeAndIsModified(INSERT_INTO_USERS, u.getTax_code(),
				u.getName(),
				u.getSurname(),
				u.getPhone(),
				u.getAddress(),
				u.getEmail(),
				u.getPassword(),
				u.getRole());

	}

	/**
	 *With this method we can delete a user finded by his id
	 * @param id
	 * @return boolean
	 */
	@Override
	public boolean delete(int id) {
		return executeAndIsModified(DELETE_USER, id);
	}

	/**
	 *With this method we can delete a comment row in our database
	 * @param u
	 * @return boolean
	 */
	@Override
	public boolean update(User u) {
		return executeAndIsModified(UPDATE_USERS + WHERE_ID, u.getTax_code(),
				u.getName(),
				u.getSurname(),
				u.getPhone(),
				u.getAddress(), 
				u.getImage(),
				u.getId());
	}

	@Override
	public User findByEmail(String email) {
		Map<String,String> map = getOne(SELECT_USERS+WHERE_EMAIL, email);
		if(map != null){
			return IMappable.fromMap(User.class ,map);
		}
		return null;
	}

	@Override
	public List<User> findByRole(String role) {
		List<User> ris = new ArrayList<>();
		List< Map <String,String>> maps = getAll(SELECT_USERS+WHERE_ROLE, role);
		for (Map<String, String> map : maps) {
			ris.add(IMappable.fromMap(User.class, map));
		}
		return ris;
	}
}
