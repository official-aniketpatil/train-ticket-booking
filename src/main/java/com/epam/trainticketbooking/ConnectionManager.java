package com.epam.trainticketbooking;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;

public class ConnectionManager {
	private static final String PROPERTY_FILE_URL = "src/main/resources/application.properties";
	private static Properties properties = new Properties();
	private static Logger logger = LogManager.getLogger(ConnectionManager.class);
	
	private ConnectionManager() {
	
	}
	
	private static void loadProperties() {
		try (InputStream inStream = new FileInputStream(PROPERTY_FILE_URL);){
			properties.load(inStream);
		} catch (IOException e) {
			logger.error("unable to load application.properties");
		}
	}

	public static Connection getDBConnection() {
		Connection connection = null;
		try {
			loadProperties();
			connection = DriverManager.getConnection(
					"jdbc:" + properties.getProperty("db.dbms") + "://" + properties.getProperty("db.serverName") + ":"
							+ properties.getProperty("db.port") + "/" + properties.getProperty("db.name"),
					properties.getProperty("db.user"), properties.getProperty("db.password"));
			return connection;
		} catch (SQLException e) {
			throw new DBConnectionFailedException("unable to connect database");
		}

	}
}
