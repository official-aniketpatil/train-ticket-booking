package com.epam.trainticketbooking.dao.impl;

import java.util.Date;

import com.epam.trainticketbooking.dao.PassengerDao;
import com.epam.trainticketbooking.dao.TicketDao;
import com.epam.trainticketbooking.dao.TrainDao;
import com.epam.trainticketbooking.model.Passenger;
import com.epam.trainticketbooking.model.Train;

public class TicketDaoImpl implements TicketDao {
	private PassengerDao passengerDao;
	private TrainDao trainDao;

	public TicketDaoImpl(PassengerDao passengerDao,TrainDao trainDao) {
		this.passengerDao = passengerDao;
		this.trainDao = trainDao;
	}
	
	@Override
	public synchronized void book(Passenger passenger, Train train, String source, String destination, String seatType,
			int seatCount, Date date) {
		if(trainDao.checkAvailability(train.getId(), date)) {
        	int count = trainDao.getAvailableSeats(train.getId(), date).get(seatType);
        	if(count >= seatCount) {
        		passengerDao.add(passenger);
        	    
        	}
        }
	}

	@Override
	public boolean cancel() {
		return false;
	}

}
