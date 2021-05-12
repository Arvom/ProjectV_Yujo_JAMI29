package com.yujo.model;
import com.yujo.util.IMappable;
public class Comment implements IMappable {
	
	private int id;
	private String content;
	private String content_time;
	private Post post;
	private User user;
	public Comment(int id, String content, String content_time, Post post, User user) {
		super();
		this.id = id;
		this.content = content;
		this.content_time = content_time;
		this.post = post;
		this.user = user;
	}
	public Comment() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContent_time() {
		return content_time;
	}
	public void setContent_time(String content_time) {
		this.content_time = content_time;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "{id:" + id + ", content:" + content + ", content_time:" + content_time + ", post:" + post + ", user:"
				+ user + "}";
	}
}
