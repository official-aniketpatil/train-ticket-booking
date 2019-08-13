package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.ConnectionManager;
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;
import com.epam.trainticketbooking.model.Train;

public class TrainDaoImpl implements TrainDao {
	private static Logger logger = LogManager.getLogger(TrainDaoImpl.class);
	private StationDao stationDao = new StationDaoImpl();
	
	private static final String GET_AVAILABLE_TRAINS = "select * from availability where date = ? and ac_seats >= ? "
			+ "and sleeper_seats >= ?";
	private static final String GET_BY_LOCATION ="select train_id from routes where station_id = ? and train_id in "
			+ "(select train_id from routes where station_id = ?)";
	private static final String GET_BY_ID = "select * from trains where id = ?";
	
	@Override
	public Train getById(long id) {
		try(Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_BY_ID)){
			    stmt.setLong(1, id);
			    ResultSet rs = stmt.executeQuery();
			    while(rs.next()) {
			    	
			    }
		} catch(DBConnectionFailedException e) {
			logger.error("connection to database failed");
		} catch(SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public List<Train> getByLocation(String source,String destination){
		List<Train> trains = new ArrayList<>();
		
		try(Connection connection = ConnectionManager.getDBConnection();
			PreparedStatement stmt = connection.prepareStatement(GET_BY_LOCATION);
			) {
			long sourceId = stationDao.getIdByName(source);
			long destinationId = stationDao.getIdByName(destination);
			
			stmt.setLong(1, sourceId);
			stmt.setLong(2, destinationId);
			ResultSet rs = stmt.executeQuery();
			
		} catch(DBConnectionFailedException ex) {
			logger.error(ex.getMessage());
		} catch(SQLException sqle) {
			logger.error(sqle.getMessage());
		}
		return Collections.emptyList();
	}

	@Override
	public List<Train> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkAvailability(long trainId, Date date) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Integer> getAvailableSeats(long trainId, Date date) {
		// TODO Auto-generated method stub
		return null;
	}
}
