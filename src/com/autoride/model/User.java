package com.autoride.model;

public class User {

	private int id;
	private String name;
	private String email;
	private String password;
	private Location location;
	private double balance;
	
	public User() {}
	

	public User(int id, String name, String email, String password, Location location, double balance) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.location = location;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", location="
				+ location + ", balance=" + balance + "]";
	}
	
}
