package com.autoride.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.autoride.dao.UserDAO;
import com.autoride.model.User;
import com.autoride.service.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
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
    		showAlert("Success", "Welcome " + user.getName() + "!");
    		goToMain(event, user);
    	}
    	else {
    		showAlert("Error", "Invalid email or password.");
    	}
    }

    @FXML
    void goToRegister(ActionEvent event) {
    	try {
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/com/autoride/ui/Register.fxml"));
            Scene registerScene = new Scene(registerRoot);
            
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(registerScene);
            window.setTitle("AutoRide - Register");
            window.show();
        } 
    	catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load register screen.");
        }
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
    
    private void goToMain(ActionEvent event, User user) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/autoride/ui/Main.fxml"));
            Parent mainRoot = loader.load();
            
            MainController mainController = loader.getController();
            mainController.updateUI(user);
            
            Scene mainScene = new Scene(mainRoot);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.setTitle("AutoRide");
            window.show();
        } 
    	catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load main screen.");
        }
    }

}
