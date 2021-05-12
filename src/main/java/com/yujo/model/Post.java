package com.yujo.model;

import com.yujo.util.IMappable;

public class Post implements IMappable{

	private int id;
	private String name;
	private String surname;
	private int id_user;
	private String content;
	private String content_time;
	
	public Post(int id, String name, String surname, int id_user, String content, String content_time) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.id_user = id_user;
		this.content = content;
		this.content_time = content_time;
	}
	
	public Post() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getsurname() {
		return surname;
	}
	public void setsurname(String surname) {
		this.surname = surname;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
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
	@Override
	public String toString() {
		return "{id:" + id + ", name:" + name + ", surname:" + surname + ", id_user:" + id_user + ", content:" + content
				+ ", content_time:" + content_time + "}";
	}
	
	
}
