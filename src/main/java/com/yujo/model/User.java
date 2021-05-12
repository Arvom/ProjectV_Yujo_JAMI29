package com.yujo.model;

import com.yujo.util.IMappable;

public class User implements IMappable{
	private int id;
    private String tax_code;
    private String name;
    private String surname;
    private String phone;
    private String address;
    
    
	public User() {
		super();
	}
	
	public User(int id, String tax_code, String name, String surname, String phone, String address) {
		super();
		this.id = id;
		this.tax_code = tax_code;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.address = address;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTax_code() {
		return tax_code;
	}
	public void setTax_code(String tax_code) {
		this.tax_code = tax_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "{id:" + id + ", tax_code:" + tax_code + ", name:" + name + ", surname:" + surname + ", phone:" + phone
				+ ", address:" + address + "}";
	}

}
