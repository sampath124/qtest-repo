package com.ticketbooking.example.service;

public class TicketServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9029913700873530388L;
	
	public TicketServiceException()
	{
		super();
	}

	public TicketServiceException(final String exMessage)
	{
		super(exMessage);
	}

}
