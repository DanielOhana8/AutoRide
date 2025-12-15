package com.autoride;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AutoRideApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent layout = FXMLLoader.load(getClass().getResource("/com/autoride/ui/Login.fxml"));
		Scene scene = new Scene(layout);
		primaryStage.setScene(scene);
		primaryStage.setTitle("AutoRide - Login");
		primaryStage.show();
	}
	
}
