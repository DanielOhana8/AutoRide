package com.autoride.dao;

import com.autoride.model.Car;
import com.autoride.model.Location;
import com.autoride.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    
    public Car getById(int id) {
        String sql = "SELECT * FROM cars WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Car(
                    rs.getInt("id"),
                    rs.getString("model"),
                    new Location(rs.getInt("location_x"), rs.getInt("location_y")),
                    rs.getBoolean("status")
                );
            } 
        } 
        catch (SQLException e) {
            System.err.println("Error getting car by ID:");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void update(Car car) {
        String sql = "UPDATE cars SET location_x = ?, location_y = ?, status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, car.getLocation().getX());
            pstmt.setInt(2, car.getLocation().getY());
            pstmt.setBoolean(3, car.getStatus());
            pstmt.setInt(4, car.getId());
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating car:");
            e.printStackTrace();
        }
    }
    
    public void save(Car car) {
        String sql = "INSERT INTO cars (model, location_x, location_y, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, car.getModel());
            pstmt.setInt(2, car.getLocation().getX());
            pstmt.setInt(3, car.getLocation().getY());
            pstmt.setBoolean(4, car.getStatus());
            
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

    public List<Car> getAvailableCars() {
        String sql = "SELECT * FROM cars WHERE status = TRUE";
        List<Car> cars = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Car car = new Car(
                    rs.getInt("id"),
                    rs.getString("model"),
                    new Location(rs.getInt("location_x"), rs.getInt("location_y")),
                    rs.getBoolean("status")
                );
                cars.add(car);
            }
        } 
        catch (SQLException e) {
            System.err.println("Error getting available cars:");
            e.printStackTrace();
        }
        
        return cars;
    }
    
    public List<Car> getAllCars() {
        String sql = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Car car = new Car(
                    rs.getInt("id"),
                    rs.getString("model"),
                    new Location(rs.getInt("location_x"), rs.getInt("location_y")),
                    rs.getBoolean("status")
                );
                cars.add(car);
            }
        } 
        catch (SQLException e) {
            System.err.println("Error getting all cars:");
            e.printStackTrace();
        }
        
        return cars;
    }
    
    public void updateStatus(int carId, Boolean status) {
        String sql = "UPDATE cars SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setBoolean(1, status);
            pstmt.setInt(2, carId);
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating car status:");
            e.printStackTrace();
        }
    }
    
}
