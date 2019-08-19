package com.epam.trainticketbooking.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.dao.impl.TrainDaoImpl;
import com.epam.trainticketbooking.model.Train;

public class TrainService {
	private TrainDaoImpl trainDao;
	private Logger logger = LogManager.getLogger(TrainService.class);

	public TrainService() {
		trainDao = new TrainDaoImpl();
	}

	public List<Train> findTrains(String source, String destination, Date date) {
		List<Train> trains = trainDao.getByLocation(source, destination);
		Iterator<Train> trainIterator = trains.iterator();
		while (trainIterator.hasNext()) {
			Train train = trainIterator.next();
			if (trainDao.checkAvailability(train.getId(), date)) {
				Map<String, Integer> seatTypeWithAvailableCount = trainDao.getAvailableSeats(train.getId(), date);
				train.setAcSeats(seatTypeWithAvailableCount.get("AC"));
				train.setSleeperSeats(seatTypeWithAvailableCount.get("SLEEPER"));
				logger.trace(train.toString());
			} else {
				trainIterator.remove();
			}
		}
		return trains;
	}
}
