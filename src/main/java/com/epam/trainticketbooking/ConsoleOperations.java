package com.epam.trainticketbooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ConsoleOperations {
	private static Logger logger = LogManager.getLogger(ConsoleOperations.class);
	private static Scanner scanner = new Scanner(System.in);
	
	private ConsoleOperations() {
		
	}
	
	public static void showServiceMenu() {
		logger.trace("Enter\n1) Show Available Trains\n");
		logger.trace("2)Book train");
	}
	
	public static void showAvailableTrainsMenu() {
		logger.trace("Enter\n1) source\n");
		logger.trace("2)Destination\n");
		logger.trace("3)Date of travelling i.e (dd-MM-yyyy)");
	}
	
	public static int getInt() {
		int choice = -1;
		try {
			choice = scanner.nextInt();
		} catch(InputMismatchException ex) {
			logger.error(ex.getMessage());
		}
		return choice;
	}
	
	public static String getString() {
		String choice = null;
		try {
			choice = scanner.next();
		} catch(InputMismatchException ex) {
			logger.error(ex.getMessage());
		}
		return choice;
	}
	
	public static Date getDate() {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		try {
			date = format.parse(scanner.next().trim());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return date;
	}
}
