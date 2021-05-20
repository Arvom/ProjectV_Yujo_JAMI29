package com.yujo.dao;

import java.util.List;

import com.yujo.model.Post;

public interface IDaoPost{

	List<Post> posts();

	List<Post> posts(int id);

    boolean add(Post p);

    boolean delete(int id);

    boolean update(Post p);
}
