package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.ConnectionManager;
import com.epam.trainticketbooking.dao.StationDao;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;

public class StationDaoImpl implements StationDao {
	private static Logger logger = LogManager.getLogger(StationDaoImpl.class);
	private static final String GET_STATION_BY_ID = "select name from stations where id = ?";
	private static final String GET_STATION_ID_BY_NAME = "select id from stations where name = ?";

	@Override
	public String getById(long id) {
		String station = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_STATION_BY_ID);) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				station = rs.getString("name");
			}

		} catch (DBConnectionFailedException ex) {
			logger.error("connection to database failed");
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		} finally {
			closeResource(rs);
		}
		return station;
	}

	@Override
	public long getIdByName(String station) {
		ResultSet rs = null;
		long stationId = -1;
		
		try (Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_STATION_ID_BY_NAME);) {
			stmt.setString(1,station);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				stationId = rs.getLong("id");
			}

		} catch (DBConnectionFailedException ex) {
			logger.error("connection to database failed");
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		} finally {
			closeResource(rs);
		}
		return stationId;
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
