package com.autoride.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.autoride.model.Location;
import com.autoride.model.User;
import com.autoride.util.DatabaseConnection;

public class UserDAO {
	
	private User userResultSet(ResultSet rs) throws SQLException{
		return new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            new Location(rs.getInt("location_x"), rs.getInt("location_y")),
            rs.getDouble("balance")
        );
	}
	
	public User getById(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return userResultSet(rs);
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting user by ID:");
            e.printStackTrace();
        }
        
        return null;
    }
	
	public User getByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return userResultSet(rs);
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting user by email:");
            e.printStackTrace();
        }
        
        return null;
	}
	
	public void update(User user) {
		String sql = "UPDATE users SET name = ?, email = ?, password = ?, location_x = ?, location_y = ?, balance = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setString(1, user.getName());
        	pstmt.setString(2, user.getEmail());
        	pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getLocation().getX());
            pstmt.setInt(5, user.getLocation().getY());
            pstmt.setDouble(6, user.getBalance());
            pstmt.setInt(7, user.getId());
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating user:");
            e.printStackTrace();
        }
	}
	
	public void save(User user) {
		String sql = "INSERT INTO users (name, email, password, location_x, location_y, balance) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setString(1, user.getName());
        	pstmt.setString(2, user.getEmail());
        	pstmt.setString(3, user.getPassword());
            pstmt.setInt(4, user.getLocation().getX());
            pstmt.setInt(5, user.getLocation().getY());
            pstmt.setDouble(6, user.getBalance());
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error saving user:");
            e.printStackTrace();
        }
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();    
        } 
        catch (SQLException e) {
            System.err.println("Error deleting user:");
            e.printStackTrace();
        }
    }
	
	public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                users.add(userResultSet(rs));
            }
        } 
        catch (SQLException e) {
            System.err.println("Error getting all users:");
            e.printStackTrace();
        }
        
        return users;
    }
	
	public void updateBalance(int id, double balance) {
		String sql = "UPDATE users SET balance = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating user:");
            e.printStackTrace();
        }
	}
	
	public void updateLocation(int id, Location location) {
		String sql = "UPDATE users SET location_x = ?, location_y = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, location.getX());
            pstmt.setInt(2, location.getY());
            pstmt.setInt(3, id);
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating user:");
            e.printStackTrace();
        }
	}
	
	public boolean authenticate(String email, String password) {
		User user = getByEmail(email);
		return user != null && password.equals(user.getPassword());
	}
	
	public boolean emailExists(String email) {
		User user = getByEmail(email);
	    return user != null;
	}

}
