package com.epam.trainticketbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.ConnectionManager;
import com.epam.trainticketbooking.exceptions.DBConnectionFailedException;

public class StationDaoImpl {
	private static Logger logger = LogManager.getLogger(StationDaoImpl.class);
	private static final String GET_STATION_QUERY = "select name from station where id = ?";

	public String getById(long id) {
		String station = null;
		ResultSet rs = null;

		try (Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement stmt = conn.prepareStatement(GET_STATION_QUERY);) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				station = rs.getString("station");
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
