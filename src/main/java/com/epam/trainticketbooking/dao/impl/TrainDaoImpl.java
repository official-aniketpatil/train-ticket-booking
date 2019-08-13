package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.ConnectionManager;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.model.Train;

public class TrainDaoImpl {
	private static Logger logger = LogManager.getLogger(TrainDaoImpl.class);
	private static final String GET_AVAILABLE_TRAINS = "select * from availability where date = ? and ac_seats >= ? and sleeper_seats >= ?";
	
	public List<Train> getByAvailibility(){
		List<Train> trains = new ArrayList<>();
		
		try(Connection connection = ConnectionManager.getDBConnection();
			PreparedStatement stmt = connection.prepareStatement(GET_AVAILABLE_TRAINS);
			) {
			Date date = Date.valueOf("00-00-0000");
			int acSeats = 0;
			int sleeperSeats = 0;
			stmt.setDate(1, date);
			stmt.setInt(2, acSeats);
			stmt.setInt(3, sleeperSeats);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
			}
			
		} catch(DBConnectionFailedException ex) {
			logger.error(ex.getMessage());
		} catch(SQLException sqle) {
			logger.error(sqle.getMessage());
		}
		return Collections.emptyList();
	}
}
