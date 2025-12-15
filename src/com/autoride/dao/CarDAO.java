package com.autoride.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.autoride.model.Car;
import com.autoride.model.Location;
import com.autoride.util.DatabaseConnection;

public class CarDAO {
	
	private Car carResultSet(ResultSet rs) throws SQLException{
		return new Car(
            rs.getInt("id"),
            rs.getString("model"),
            new Location(rs.getInt("location_x"), rs.getInt("location_y")),
            rs.getBoolean("available")
        );
	}
    
    public Car getById(int id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return carResultSet(rs);
            } 
        } 
        catch (SQLException e) {
            System.err.println("Error getting car by ID:");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void update(Car car) {
        String sql = "UPDATE cars SET location_x = ?, location_y = ?, available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, car.getLocation().getX());
            pstmt.setInt(2, car.getLocation().getY());
            pstmt.setBoolean(3, car.isAvailable());
            pstmt.setInt(4, car.getId());
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating car:");
            e.printStackTrace();
        }
    }
    
    public void save(Car car) {
        String sql = "INSERT INTO cars (model, location_x, location_y, available) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, car.getModel());
            pstmt.setInt(2, car.getLocation().getX());
            pstmt.setInt(3, car.getLocation().getY());
            pstmt.setBoolean(4, car.isAvailable());
            
            pstmt.executeUpdate();            
        } 
        catch (SQLException e) {
            System.err.println("Error saving car:");
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM cars WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();    
        } 
        catch (SQLException e) {
            System.err.println("Error deleting car:");
            e.printStackTrace();
        }
    }

    public List<Car> getAllAvailableCars() {
        String sql = "SELECT * FROM cars WHERE available = TRUE";
        List<Car> cars = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                cars.add(carResultSet(rs));
            }
        } 
        catch (SQLException e) {
            System.err.println("Error getting all available cars:");
            e.printStackTrace();
        }
        
        return cars;
    }
    
    public List<Car> getAllCars() {
        String sql = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
           	
           	ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                cars.add(carResultSet(rs));
            }
        } 
        catch (SQLException e) {
            System.err.println("Error getting all cars:");
            e.printStackTrace();
        }
        
        return cars;
    }
    
    public void setAvailability(int carId, Boolean available) {
        String sql = "UPDATE cars SET available = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, available);
            pstmt.setInt(2, carId);
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating car availability:");
            e.printStackTrace();
        }
    }

	public void setLocation(int carId, Location location) {
		String sql = "UPDATE cars SET location_x = ?, location_y = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setInt(1, location.getX());
            pstmt.setInt(2, location.getY());
            pstmt.setInt(3, carId);
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating car location:");
            e.printStackTrace();
        }
	}
    
}
