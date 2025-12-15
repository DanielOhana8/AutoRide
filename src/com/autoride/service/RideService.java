package com.autoride.service;

import java.time.LocalDateTime;
import java.util.List;

import com.autoride.dao.RideDAO;
import com.autoride.model.Car;
import com.autoride.model.Location;
import com.autoride.model.Ride;
import com.autoride.model.User;

public class RideService {
	
	private RideDAO rideDAO;
	private CarService carService;
	private UserService userService;
	
	private static final double PRICE_PER_UNIT = 5;
	
	public RideService(RideDAO rideDAO, CarService carService, UserService userService) {
		this.rideDAO = rideDAO;
		this.carService = carService;
		this.userService = userService;
	}

	public Ride startRide(int userId) {
		User user = userService.getUserById(userId);
		if (user == null || rideDAO.getActiveRideByUser(userId) != null || user.getBalance() <= 0) {
			return null;
		}
		
		Car car = carService.findClosestAvailableCar(user.getLocation());
		if (car == null) {
			return null;
		}
		
		Ride ride = new Ride(0, userId, car.getId(), LocalDateTime.now(), null, user.getLocation(), null, 0.0);
		
		rideDAO.save(ride);
		carService.setCarAvailability(car.getId(), false);
		return rideDAO.getActiveRideByUser(userId);
	}
	
	public void endRide(int userId, Location endLocation) {
		User user = userService.getUserById(userId);
		if (user == null || rideDAO.getActiveRideByUser(userId) == null) {
			return;
		}
		
		Ride ride = rideDAO.getActiveRideByUser(userId);
		Car car = carService.getCarById(ride.getCarId());
		
		double totalCost = PRICE_PER_UNIT * ride.getStartLocation().distanceTo(endLocation);
		
		userService.updateBalance(userId, -totalCost);
		rideDAO.endRide(ride.getId(), LocalDateTime.now(), endLocation, totalCost);
		carService.setCarAvailability(car.getId(), true);
		carService.setCarLocation(car.getId(), endLocation);
	}
	
	public Ride getUserActiveRide(int userId) {
        return rideDAO.getActiveRideByUser(userId);
    }
    
    public List<Ride> getUserRideHistory(int userId) {
        return rideDAO.getRidesByUser(userId);
    }
    
    public List<Ride> getCarRideHistory(int carId) {
        return rideDAO.getRidesByCar(carId);
    }

}
