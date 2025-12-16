package com.autoride.model;

public class Car {
	
	private int id;
	private String model;
	private Location location;
	private boolean available;
	
	public Car() {}

	public Car(int id, String model, Location location, boolean available) {
		this.id = id;
		this.model = model;
		this.location = location;
		this.available = available;
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
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", model=" + model + ", location=" + location + ", available=" + available + "]";
	}
	
}
