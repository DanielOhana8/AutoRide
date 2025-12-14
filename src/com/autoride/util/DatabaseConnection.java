package com.autoride.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            
            // Load the properties file from the classpath (src folder)
            InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties");
            
            if (input == null) {
                System.err.println("Error: config.properties file not found in classpath!");
                return null;
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}