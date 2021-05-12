package com.yujo.dao;

import java.util.List;

import com.yujo.model.Comment;

public interface IDaoComment {

	List<Comment> comments(int id_post);

    boolean add(Comment c);

    boolean delete(int id);

    boolean update(Comment c);

}
