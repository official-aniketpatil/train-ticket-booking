package com.epam.trainticketbooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.services.TrainService;

public class ApplicationStarter {
	private static Logger logger = LogManager.getLogger(ApplicationStarter.class);
	
	public static void main(String[] args) {
		TrainService trainService = new TrainService();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = format.parse("12-08-2019");
			trainService.findTrains("pune","bhopal",date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
	}

}
