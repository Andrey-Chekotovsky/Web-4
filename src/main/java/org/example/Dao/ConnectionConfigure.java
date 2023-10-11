package org.example.Dao;

import lombok.Getter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionConfigure {

    static private String url;
    static private String user;
    static private  String password;
    @Getter
    static private Connection connection;
    static {
        Properties prop = new Properties();
        try {
            prop.load(ConnectionConfigure.class.getClassLoader().getResourceAsStream("application.properties"));


            url = prop.getProperty("jdbc.url");
            user = prop.getProperty("jdbc.user");
            password = prop.getProperty("jdbc.password");
            try {
                connection = DriverManager.getConnection(url, user, password);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
