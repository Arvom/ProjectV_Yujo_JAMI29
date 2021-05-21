package com.yujo.dao;

import java.util.List;

import com.yujo.model.Post;

public interface IDaoPost{

	List<Post> posts();

	List<Post> posts(int id);

    Post post(int id_post);

    boolean add(Post p);

    boolean delete(int id);

    boolean update(Post p);
}
