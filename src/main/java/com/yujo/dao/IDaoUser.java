package com.yujo.dao;

import java.util.List;
import java.util.Optional;

import com.yujo.model.Option;
import com.yujo.model.User;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;

public interface IDaoUser {
	List<User> users();

    User user(int id);

    boolean add(User u);

    boolean delete(int id);

    boolean update(User u);

    User findByEmail(String email);

    List<User>findByRole(String role);
}
