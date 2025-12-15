package com.autoride.service;

import com.autoride.dao.UserDAO;
import com.autoride.model.Location;
import com.autoride.model.User;
import com.autoride.util.PasswordUtil;

public class UserService {
	
	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public User register(User user) {
		if (userDAO.emailExists(user.getEmail())) {
			return null;
		}
		
		String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
		user.setPassword(hashedPassword);
		
		userDAO.save(user);
		return user;
	}
	
	public User login(String email, String Password) {
		User user = userDAO.getByEmail(email);
		
		if (user != null && PasswordUtil.checkPassword(Password, user.getPassword())) {
			return user;
		}
		
		return null;
	}
	
	public User getUserById(int id) {
		return userDAO.getById(id);
	}
	
	public void updateBalance(int id, double amount) {
		User user = getUserById(id);
		
		if (user != null) {
			userDAO.updateBalance(id, user.getBalance() + amount);
		}
	}

	public void updateUserLocation(int id, Location location) {
		userDAO.updateLocation(id, location);
	}
	
	public void updateProfile(User user) {
		userDAO.update(user);
	}
	
}
