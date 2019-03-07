package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.TicketDAO;
import com.revature.models.Ticket;

public class TicketService {

	private TicketDAO ticketDao = new TicketDAO();
	
	public ArrayList<Ticket> getbyAuthorId(int authorId){
		return ticketDao.getByAuthorId(authorId);
	}
	
	public ArrayList<Ticket> getAll(){
		return ticketDao.getAll();
	}
	
	public Ticket getById(int ticketId) {
		return ticketDao.getById(ticketId);
	}
	
	public Ticket add(Ticket newTicket) {
		return ticketDao.add(newTicket);
	}
	
	public ArrayList<Ticket> update(ArrayList<Ticket> updatedTickets){
		return ticketDao.update(updatedTickets);
	}
	
	// TicketDAO.delete() is not implemented
	public boolean delete(int ticketId) {
		return ticketDao.delete(ticketId);
	}
}