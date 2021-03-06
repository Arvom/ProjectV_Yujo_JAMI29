package com.yujo.dao;

import java.util.List;
import com.yujo.model.User;

public interface IDaoUser {
	List<User> users();

    User user(int id);

    boolean add(User u);

    boolean delete(int id);

    boolean update(User u);

    User findByEmail(String email);

    List<User>findByRole(String role);

}
