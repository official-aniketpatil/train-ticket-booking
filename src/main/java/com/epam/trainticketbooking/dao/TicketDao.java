package com.epam.trainticketbooking.dao;

import java.util.Date;

import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public interface TicketDao {
	
	public boolean cancel();
	public void book(Passenger passenger, Train train, String source, String destination, String seatType, int seatCount,
			Date date);
}
