package com.epam.trainticketbooking;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.epam.trainticketbooking.services.TrainService;

public class ApplicationStarter {
	private static Logger logger = LogManager.getLogger(ApplicationStarter.class);
	private static final int SEARCH_TRAINS = 1;
	private static final int BOOK_TRAIN = 2;

	public static void main(String[] args) {
		TrainService trainService = new TrainService();
		ConsoleOperations.showServiceMenu();
		int choice = ConsoleOperations.getInt();

		if (choice == SEARCH_TRAINS) {
			ConsoleOperations.showAvailableTrainsMenu();
			String source = ConsoleOperations.getString();
			String destination = ConsoleOperations.getString();
			Date date = ConsoleOperations.getDate();
			trainService.findTrains(source, destination, date);
		} else if (choice == BOOK_TRAIN) {

		} else {
			logger.error("Enter a valid choice");
		}

	}

}
