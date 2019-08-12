package com.epam.trainticketbooking.model;

public class Train {
	private long id;
	private String source;
	private String destination;
	private double fare;
	
	public Train(long id, String source, String destination, double fare) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.fare = fare;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	@Override
	public String toString() {
		return "Train [id=" + id + ", source=" + source + ", destination=" + destination + ", fare=" + fare + "]";
	}
}
