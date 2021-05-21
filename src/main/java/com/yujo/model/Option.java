package com.yujo.model;


import com.yujo.util.IMappable;

/**
 *this class represents the tabular entity options on our database
 */
public class Option implements IMappable{

	private int id;
	private User user;
	private String content;
	private String id_users;
	private Post post;
	private int vote;
	

	public Option(int id, User user, String content, String id_users, Post post, int vote) {
		super();
		this.id = id;
		this.user = user;
		this.content = content;
		this.id_users = id_users;
		this.post = post;
		this.vote = vote;
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

	
	public int getVote() {
		return vote;
	}


	public void setVote(int vote) {
		this.vote = vote;
	}


	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", user:" + user + ", content:" + content + ", id_users:" + id_users + ", post:" + post
				+ ", vote:" + vote + "}";
	}
	
}