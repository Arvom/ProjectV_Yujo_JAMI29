package com.yujo.model;


import com.yujo.util.IMappable;

public class Option implements IMappable{

	private int id;
	private User user;
	private String content;
	private String id_users;
	private Post post;
	
	public Option(int id, User user, String content, String id_users, Post post) {
		super();
		this.id = id;
		this.user = user;
		this.content = content;
		this.id_users = id_users;
		this.post = post;
	}
	

	public Option() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId_users() {
		return id_users;
	}

	public void setId_users(String id_users) {
		this.id_users = id_users;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", user:" + user + ", content:" + content + ", id_users:" + id_users + ", post:" + post
				+ "}";
	}
	
}