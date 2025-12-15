package com.autoride.service;

import java.util.List;

import com.autoride.dao.CarDAO;
import com.autoride.model.Car;
import com.autoride.model.Location;

public class CarService {
	
	private CarDAO carDAO;

	public CarService(CarDAO carDAO) {
		this.carDAO = carDAO;
	}
	
	public Car findClosestAvailableCar(Location userLocation) {
		List<Car> cars = carDAO.getAllAvailableCars();
		Car closestCar = null;
		double closestCarDistance = Double.MAX_VALUE;
		
		for (Car car : cars) {
			if (userLocation.distanceTo(car.getLocation()) < closestCarDistance) {
				closestCar = car;
				closestCarDistance = userLocation.distanceTo(car.getLocation());
			}
		}
		
		return closestCar;
	}
	
	public void setCarAvailability(int id, boolean available) {
		carDAO.setAvailability(id, available);
	}
	
	public void setCarLocation(int id, Location location) {
		carDAO.setLocation(id, location);
	}
	
	public Car getCarById(int id) {
		return carDAO.getById(id);
	}
	
	public List<Car> getAllAvailableCars() {
		return carDAO.getAllAvailableCars();
	}
	
}
