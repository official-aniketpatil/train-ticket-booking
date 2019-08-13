package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private static final String FAILED_CONNECTION_MESSAGE = "connection to database failed";
	private static final String GET_BY_LOCATION = "select train_id from routes where station_id = ? and train_id in "
			+ "(select train_id from routes where station_id = ?)";
	private static final String GET_BY_ID = "select * from trains where id = ?";
	private static final String GET_AVAILABLE_SEATS = "select ac_seats,sleeper_seats from availability where train_id = ? "
			+ "and date = ?";
	private static final String CHECK_AVAILABILITY = "select * from availability where train_id = ? and date = ?";

	@Override
	public Train getById(long id) {
		ResultSet rs = null;
		Train train = new Train();
		try (Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_BY_ID)) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String source = stationDao.getById(rs.getLong("source_station"));
				String destination = stationDao.getById(rs.getLong("destination_station"));
				long distance = rs.getLong("distance");
				train.setId(id);
				train.setSource(source);
				train.setDestination(destination);
				train.setDistance(distance);
			}
		} catch (DBConnectionFailedException e) {
			logger.error(FAILED_CONNECTION_MESSAGE);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			closeResource(rs);
		}
		return train;
	}

	@Override
	public List<Train> getByLocation(String source, String destination) {
		List<Train> trains = new ArrayList<>();
		ResultSet rs = null;

		try (Connection connection = ConnectionManager.getDBConnection();
				PreparedStatement stmt = connection.prepareStatement(GET_BY_LOCATION);) {
			long sourceId = stationDao.getIdByName(source);
			long destinationId = stationDao.getIdByName(destination);

			stmt.setLong(1, sourceId);
			stmt.setLong(2, destinationId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				Train train = getById(rs.getLong("train_id"));
				trains.add(train);
			}

		} catch (DBConnectionFailedException ex) {
			logger.error(ex.getMessage());
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		} finally {
			closeResource(rs);
		}
		return trains;
	}

	@Override
	public List<Train> getAll() {
		return null;
	}

	@Override
	public boolean checkAvailability(long trainId, Date date) {
		ResultSet rs = null;
		try (Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_AVAILABLE_SEATS);) {
			stmt.setLong(1, trainId);
			stmt.setString(2, date.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				return true;
			}
		} catch (DBConnectionFailedException e) {
			logger.error(FAILED_CONNECTION_MESSAGE);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			closeResource(rs);
		}
		return false;
	}

	@Override
	public Map<String, Integer> getAvailableSeats(long trainId, Date date) {
		Map<String, Integer> seatTypeWithAvailableCount = new HashMap<>();
		ResultSet rs = null;

		try (Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_AVAILABLE_SEATS);) {
			stmt.setLong(1, trainId);
			stmt.setString(2, date.toString());
			rs = stmt.executeQuery();
			while (rs.next()) {
				seatTypeWithAvailableCount.put("AC", rs.getInt("ac_seats"));
				seatTypeWithAvailableCount.put("SLEEPER", rs.getInt("sleeper_seats"));
			}
		} catch (DBConnectionFailedException e) {
			logger.error(FAILED_CONNECTION_MESSAGE);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			closeResource(rs);
		}

		return seatTypeWithAvailableCount;
	}

	private void closeResource(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
