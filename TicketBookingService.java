package com.ticketbooking.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticketbooking.example.dao.TicketBookingDao;
import com.ticketbooking.example.exception.RequiredFiledsException;
import com.ticketbooking.example.exception.TicketNotFoundException;
import com.ticketbooking.example.model.Ticket;

@Service
public class TicketBookingService {

	@Autowired
	private TicketBookingDao ticketDao;

	// TicketBookingDao ticketDao=new TicketBookingDao();

	// GET ALL TICKET METHODS

	public Iterable<Ticket> getAllTickets() {
		return ticketDao.findAll();
		// select * from ticket_tbl -> output will tranlated List<Ticket>

	}

	// GET TICKET METHOD

	public Ticket getTicketById(Integer ticketId) {

		return ticketDao.findById(ticketId). // Till here it executes if we have the data with input
				orElse(new Ticket()); // If we dont have data rather than returning null its return dummy object

		// Optional keyword

	}
	
	public Ticket findByPassengerName(String  passengerName) {

		return ticketDao.findByPassengerName(passengerName);

	}

	// CREATE TICKET METHOD

	public Ticket createTicket(Ticket ticket) throws RequiredFiledsException, TicketServiceException {

		if (ticket.getPassengerName() == null) {
			System.out.println("Missing Required Filds : Passengername");
			throw new RequiredFiledsException("Required Fileds : Passengername");
		} else {
			try {
				ticket = ticketDao.save(ticket);
				if (ticket.getTicketId() <= 0) {
					throw new TicketServiceException();
				}
			} catch (Throwable th) {
				throw new TicketServiceException(th.getMessage());
			}

		}

		return ticket;
		// Save is common for Insert and update

		// Insert into ticket_table(ticket....);,

	}
	// UPDATE TICKET METHOD

	public Ticket updateTicket(Integer ticketId, String newEmail) {
		// WE can perform save method to update the data only on persistent object and
		// persistent object means the data retrieved from database is called as
		// persisent object

		Ticket dbTicketObj = getTicketById(ticketId);
		dbTicketObj.setEmail(newEmail);
		return ticketDao.save(dbTicketObj);

	}

	// DELETE TICKET METHOD

	public void deleteTicket(Integer ticketId) throws TicketNotFoundException, TicketServiceException {

		Ticket dbTicketObj = getTicketById(ticketId);
		if (dbTicketObj == null || (dbTicketObj != null && dbTicketObj.getPassengerName() == null)) {
			System.out.println("Ticket Id not Avaible during Delete");
			throw new TicketNotFoundException("Ticket Id not Avaible");
		} else {
			try {
				ticketDao.deleteById(ticketId);
			} catch (Throwable th) {
				System.out.println("Exception in delete message");
				throw new TicketServiceException(th.getMessage());
			}
		}

		// delete ticket_tbl where ticket_id=ticketId

	}

	
}
