package com.yujo.model;

import com.yujo.security.Role;
import com.yujo.util.IMappable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class User implements IMappable, UserDetails{

	private static final Map<String, Collection<? extends GrantedAuthority>> AUTHORITIES = new HashMap<>();

	{
		AUTHORITIES.put(Role.ADMIN, Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority("ROLE_ADMIN"),
				new SimpleGrantedAuthority("management"),}));
		AUTHORITIES.put(Role.USER, Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority("ROLE_USER")}));

	}

	public static Collection<? extends GrantedAuthority> getAuthorityOfRole(String role) {
		return AUTHORITIES.get(role);
	}



	private int id;
	private String email;
	private String password;
	private String tax_code;
	private String name;
	private String surname;
	private String phone;
	private String address;
	private String role;


	public User() {
		super();
	}

	public User(int id, String email, String password, String tax_code, String name, String surname, String phone, String address, String role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.tax_code = tax_code;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.address = address;
		this.role = role;
	}

	public User(String email, String password, String tax_code, String name, String surname, String phone, String address, String role) {
		this.email = email;
		this.password = password;
		this.tax_code = tax_code;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.address = address;
		this.role = role;
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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getAuthorityOfRole(this.role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", tax_code='" + tax_code + '\'' +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", role='" + role + '\'' +
				'}';
	}

}