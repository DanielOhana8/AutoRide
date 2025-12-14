package com.autoride.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.autoride.model.Location;
import com.autoride.model.Ride;
import com.autoride.util.DatabaseConnection;

public class RideDAO {
	
	public Ride getById(int id) {
		String sql = "SELECT * FROM rides WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting ride by ID:");
            e.printStackTrace();
        }
        
        return null;
	}
	
	public void update(Ride ride) {
		String sql = "UPDATE rides SET start_time = ?, end_time = ?, start_location_x = ?, start_location_y = ?, "
				+ "end_location_x = ?, end_location_y = ?, total_cost = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setTimestamp(1, Timestamp.valueOf(ride.getStartTime()));
        	if (ride.getEndTime() != null) {
        		pstmt.setTimestamp(2, Timestamp.valueOf(ride.getEndTime()));
        	}
        	else {
        		pstmt.setNull(2, java.sql.Types.TIMESTAMP);
        	}
            pstmt.setInt(3, ride.getStartLocation().getX());
            pstmt.setInt(4, ride.getStartLocation().getY());
            if (ride.getEndLocation() != null) {
            	pstmt.setInt(5, ride.getEndLocation().getX());
            	pstmt.setInt(6, ride.getEndLocation().getY());
            }
            else {
            	pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            pstmt.setDouble(7, ride.getTotalCost());
            pstmt.setInt(8, ride.getId());
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error updating ride:");
            e.printStackTrace();
        }
	}
	
	public void save(Ride ride) {
		String sql = "INSERT INTO rides (user_id, car_id, start_time, end_time, start_location_x, start_location_y, "
				+ "end_location_x, end_location_y, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setInt(1, ride.getUserId());
        	pstmt.setInt(2, ride.getCarId());
        	pstmt.setTimestamp(3, Timestamp.valueOf(ride.getStartTime()));
        	if (ride.getEndTime() != null) {
        		pstmt.setTimestamp(4, Timestamp.valueOf(ride.getEndTime()));
        	}
        	else {
        		pstmt.setNull(4, java.sql.Types.TIMESTAMP);
        	}
            pstmt.setInt(5, ride.getStartLocation().getX());
            pstmt.setInt(6, ride.getStartLocation().getY());
            if (ride.getEndLocation() != null) {
            	pstmt.setInt(7, ride.getEndLocation().getX());
            	pstmt.setInt(8, ride.getEndLocation().getY());
            }
            else {
            	pstmt.setNull(7, java.sql.Types.INTEGER);
                pstmt.setNull(8, java.sql.Types.INTEGER);
            }
            pstmt.setDouble(9, ride.getTotalCost());
            
            pstmt.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println("Error saving ride:");
            e.printStackTrace();
        }
	}
	
	public void delete(int id) {
		String sql = "DELETE FROM rides WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();    
        } 
        catch (SQLException e) {
            System.err.println("Error deleting ride:");
            e.printStackTrace();
        }
	}
	
	public List<Ride> getActiveRides() {
		String sql = "SELECT * FROM rides WHERE end_time IS NULL";
		List<Ride> rides = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
                rides.add(ride);
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting active rides:");
            e.printStackTrace();
        }
        
        return rides;
	}

	public List<Ride> getAllRides() {
		String sql = "SELECT * FROM rides";
		List<Ride> rides = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
                rides.add(ride);
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting all rides:");
            e.printStackTrace();
        }
        
        return rides;
	}
	
	public List<Ride> getRidesByUser(int userId) {
		String sql = "SELECT * FROM rides WHERE user_id = ?";
		List<Ride> rides = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
                rides.add(ride);
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting rides by user:");
            e.printStackTrace();
        }
        
        return rides;
	}
	
	public List<Ride> getRidesByCar(int carId) {
		String sql = "SELECT * FROM rides WHERE car_id = ?";
		List<Ride> rides = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Ride ride = new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
                rides.add(ride);
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting rides by car:");
            e.printStackTrace();
        }
        
        return rides;
	}
	
	public Ride getActiveRideByUser(int userId) {
		String sql = "SELECT * FROM rides WHERE user_id = ? AND end_time IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting rides by user:");
            e.printStackTrace();
        }
        
        return null;
	}
	
	public Ride getActiveRideByCar(int carId) {
		String sql = "SELECT * FROM rides WHERE car_id = ? AND end_time IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
        	pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Ride(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("car_id"),
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime(),
                    new Location(rs.getInt("start_location_x"), rs.getInt("start_location_y")),
                    new Location(rs.getInt("end_location_x"), rs.getInt("end_location_y")),
                    rs.getDouble("total_cost")
                );
            }         
        } 
        catch (SQLException e) {
            System.err.println("Error getting rides by user:");
            e.printStackTrace();
        }
        
        return null;
	}
	
	public void endRide(int id, LocalDateTime endTime, Location endLocation, double totalCost) {
		Ride ride = getById(id);
		
		if (ride != null) {
			ride.setEndTime(endTime);
			ride.setEndLocation(endLocation);
			ride.setTotalCost(totalCost);
			
			update(ride);
		}
	}
	
}
