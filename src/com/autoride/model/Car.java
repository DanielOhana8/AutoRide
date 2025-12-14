package com.autoride.model;

public class Car {
	
	private int id;
	private String model;
	private Location location;
	private String status;
	
	public Car() {}

	public Car(int id, String model, Location location, String status) {
		this.id = id;
		this.model = model;
		this.location = location;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", location=" + location + ", status=" + status + "]";
	}
	
}
