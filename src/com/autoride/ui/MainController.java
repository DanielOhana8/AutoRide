package com.autoride.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.autoride.dao.CarDAO;
import com.autoride.dao.RideDAO;
import com.autoride.dao.UserDAO;
import com.autoride.model.Car;
import com.autoride.model.Location;
import com.autoride.model.User;
import com.autoride.service.CarService;
import com.autoride.service.RideService;
import com.autoride.service.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController {
	
	private UserService userService = new UserService(new UserDAO());
	private CarService carService = new CarService(new CarDAO());
	private RideService rideService = new RideService(new RideDAO(), carService, userService);
	private User user;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text activeRideText;

    @FXML
    private Text balanceText;

    @FXML
    private TextField balanceAddText;

    @FXML
    private Button endRideButton;

    @FXML
    private Text locationText;

    @FXML
    private Text nameText;

    @FXML
    private Button startRideButton;

    @FXML
    private TextField xChangeText;

    @FXML
    private TextField xRideText;

    @FXML
    private TextField yChangeText;

    @FXML
    private TextField yRideText;

    @FXML
    void addToBalance(ActionEvent event) {
    	try {
			userService.updateBalance(user.getId(), Double.valueOf(balanceAddText.getText()));
			user = userService.getUserById(user.getId());
			balanceText.setText(Double.toString(user.getBalance()) + " ₪");
			balanceAddText.setText("");
		} 
    	catch (NumberFormatException e) {
			showAlert("Error", "Not a number.");
		}
    }
    
    @FXML
    void changeLocation(ActionEvent event) {
    	if (rideService.getUserActiveRide(user.getId()) != null) {
    		showAlert("Error", "You can't change location in a ride.");
    		return;
    	}

    	try {
			Location location = new Location(Integer.valueOf(xChangeText.getText()), Integer.valueOf(yChangeText.getText()));
			xChangeText.setText("");
			yChangeText.setText("");
			
			userService.updateUserLocation(user.getId(), location);
			user = userService.getUserById(user.getId());
			locationText.setText(user.getLocation().toString());
		} 
    	catch (NumberFormatException e) {
			showAlert("Error", "Not a numbers.");
		}
    }

    @FXML
    void endRide(ActionEvent event) {
    	try {
			Location location = new Location(Integer.valueOf(xRideText.getText()), Integer.valueOf(yRideText.getText()));
			xRideText.setText("");
			yRideText.setText("");
			
			rideService.endRide(user.getId(), location);
			user = userService.getUserById(user.getId());
			locationText.setText(user.getLocation().toString());
			balanceText.setText(Double.toString(user.getBalance()) + " ₪");
			activeRideText.setText("you are not in a ride");
			endRideButton.setDisable(true);
			startRideButton.setDisable(false);
		} 
    	catch (NumberFormatException e) {
    		showAlert("Error", "Not a numbers.");
		}
    }

    @FXML
    void logout(ActionEvent event) {
    	goToLogin(event);
    }

    @FXML
    void startRide(ActionEvent event) {
    	rideService.startRide(user.getId());
    	
    	if (rideService.getUserActiveRide(user.getId()) == null) {
    		showAlert("Error", "Not found available car.");
    		return;
    	}
    	
    	user = userService.getUserById(user.getId());
    	Car car = carService.getCarById(rideService.getUserActiveRide(user.getId()).getCarId());
    	
    	activeRideText.setText("you are in a ride on " + car.getModel());
    	startRideButton.setDisable(true);
    	endRideButton.setDisable(false);
    }

    @FXML
    void initialize() {
        assert activeRideText != null : "fx:id=\"activeRideText\" was not injected: check your FXML file 'Main.fxml'.";
        assert balanceText != null : "fx:id=\"balanceText\" was not injected: check your FXML file 'Main.fxml'.";
        assert balanceAddText != null : "fx:id=\"balnceAddText\" was not injected: check your FXML file 'Main.fxml'.";
        assert endRideButton != null : "fx:id=\"endRideButton\" was not injected: check your FXML file 'Main.fxml'.";
        assert locationText != null : "fx:id=\"locationText\" was not injected: check your FXML file 'Main.fxml'.";
        assert nameText != null : "fx:id=\"nameText\" was not injected: check your FXML file 'Main.fxml'.";
        assert startRideButton != null : "fx:id=\"startRideButton\" was not injected: check your FXML file 'Main.fxml'.";
        assert xChangeText != null : "fx:id=\"xChangeText\" was not injected: check your FXML file 'Main.fxml'.";
        assert xRideText != null : "fx:id=\"xRideText\" was not injected: check your FXML file 'Main.fxml'.";
        assert yChangeText != null : "fx:id=\"yChangeText\" was not injected: check your FXML file 'Main.fxml'.";
        assert yRideText != null : "fx:id=\"yRideText\" was not injected: check your FXML file 'Main.fxml'.";
    }

    public void setUser(User user) {
		this.user = user;
	}

	public void updateUI(User user) {
    	setUser(user);
        nameText.setText(user.getName());
        balanceText.setText(Double.toString(user.getBalance()) + " ₪");
        locationText.setText(user.getLocation().toString());
        if (rideService.getUserActiveRide(user.getId()) == null) {
        	endRideButton.setDisable(true);
        }
        else {
        	startRideButton.setDisable(true);
        }
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(title.equals("Error") ? AlertType.ERROR : AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private void goToLogin(ActionEvent event) {
    	try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/autoride/ui/Login.fxml"));
            Scene loginScene = new Scene(loginRoot);
            
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(loginScene);
            window.setTitle("AutoRide - Login");
            window.show();
        } 
    	catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load login screen.");
        }
    }
    
}
