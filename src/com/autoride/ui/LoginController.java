package com.autoride.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.autoride.dao.UserDAO;
import com.autoride.model.User;
import com.autoride.service.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	private UserService userService = new UserService(new UserDAO());
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField passwordTextField;

    @FXML
    private CheckBox showPasswordBox;

    @FXML
    void login(ActionEvent event) {
    	String email = emailField.getText();
    	String password = passwordField.getText();
    	
    	if (email.isEmpty() || password.isEmpty()) {
    		showAlert("Error", "Please enter email and password.");
    		return;
    	}
    	
    	User user = userService.login(email, password);
    	
    	if (user != null) {
    		showAlert("Success", "Welcome " + user.getName());
    	}
    	else {
    		showAlert("Error", "Invalid email or password.");
    	}
    }

    @FXML
    void goToRegister(ActionEvent event) {
    	
    }

    @FXML
    void initialize() {
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'Login.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'Login.fxml'.";
        assert showPasswordBox != null : "fx:id=\"showPasswordBox\" was not injected: check your FXML file 'Login.fxml'.";
        
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
