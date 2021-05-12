package com.yujo.model;

import com.yujo.util.IMappable;

public class Post implements IMappable{

	private int id;
	private String nome;
	private String cognome;
	private int id_user;
	private String content;
	private String content_time;
	
	public Post(int id, String nome, String cognome, int id_user, String content, String content_time) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
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
		return "{id:" + id + ", nome:" + nome + ", cognome:" + cognome + ", id_user:" + id_user + ", content:" + content
				+ ", content_time:" + content_time + "}";
	}
	
	
}
