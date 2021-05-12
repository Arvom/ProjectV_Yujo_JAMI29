package com.yujo.dao;

import java.util.List;

import com.yujo.model.Post;

public interface IDaoPost{

	List<Post> posts();

    Post post(int id);

    boolean add(Post p, int id_user);

    boolean delete(int id);

    boolean update(String content);
}
