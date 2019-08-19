package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.ConnectionManager;
import com.epam.trainticketbooking.dao.PassengerDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.model.Passenger;

public class PassengerDaoImpl implements PassengerDao {
	private static Logger logger = LogManager.getLogger(PassengerDaoImpl.class);
	private static final String ADD_PASSENGER = "insert into passengers(name,gender,mobile) values(?, ?, ?)";

	@Override
	public Passenger add(Passenger passenger) {
		try (Connection connection = ConnectionManager.getDBConnection();
				PreparedStatement statement = connection.prepareStatement(ADD_PASSENGER);) {
			statement.setString(1, passenger.getName());
			statement.setString(2, passenger.getGender());
			statement.setString(3, passenger.getMobile());
			statement.executeUpdate();
		} catch (DBConnectionFailedException | SQLException ex) {
			logger.error(ex.getMessage());
		}
		return passenger;
	}

}
