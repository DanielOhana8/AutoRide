package com.autoride.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.autoride.dao.UserDAO;
import com.autoride.model.User;
import com.autoride.service.UserService;
import com.autoride.model.Location;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class RegisterController {

	private UserService userService = new UserService(new UserDAO());
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox showPasswordBox;

    @FXML
    void register(ActionEvent event) {
    	String email = emailField.getText();
    	String password = passwordField.getText();
    	String name = nameField.getText();
    	
    	if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
    		showAlert("Error", "Please enter name, email and password.");
    		return;
    	}
    	
    	User user = userService.register(new User(0, name, email, password, new Location(0,0), 0.0));
    	
    	if (user != null) {
    		showAlert("Success", "Welcome " + user.getName() + "! please login.");
    		goToLogin(event);
    	}
    	else {
    		showAlert("Error", "User exists.");
    	}
    }
    
    @FXML
    void goToLogin(ActionEvent event) {
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

    @FXML
    void initialize() {
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'Register.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'Register.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Register.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Register.fxml'.";
        assert showPasswordBox != null : "fx:id=\"showPasswordBox\" was not injected: check your FXML file 'Register.fxml'.";

        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
        
        passwordTextField.visibleProperty().bind(showPasswordBox.selectedProperty());
        passwordTextField.managedProperty().bind(showPasswordBox.selectedProperty());
        
        passwordField.visibleProperty().bind(showPasswordBox.selectedProperty().not());
        passwordField.managedProperty().bind(showPasswordBox.selectedProperty().not());
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(title.equals("Error") ? AlertType.ERROR : AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
