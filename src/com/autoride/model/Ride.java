package com.autoride.model;

import java.time.LocalDateTime;

public class Ride {

	private int id;
	private int userId;
	private int carId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private double totalCost;
	
	public Ride() {}
	
	public Ride(int id, int userId, int carId, LocalDateTime startTime, LocalDateTime endTime, double totalCost) {
		super();
		this.id = id;
		this.userId = userId;
		this.carId = carId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.totalCost = totalCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "Ride [id=" + id + ", userId=" + userId + ", carId=" + carId + ", startTime=" + startTime + ", endTime="
				+ endTime + ", totalCost=" + totalCost + "]";
	}
	
}
