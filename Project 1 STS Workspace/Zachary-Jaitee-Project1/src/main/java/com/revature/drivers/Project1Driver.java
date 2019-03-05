package com.revature.drivers;

import java.util.ArrayList;

import com.revature.dao.TicketDAO;
import com.revature.models.Ticket;

public class Project1Driver {
	public static void main(String[] args) {
		System.out.println("Hello Project 1!");
		
		TicketDAO ticketDao = new TicketDAO();
		
		ArrayList<Ticket> ticketList = ticketDao.getAll();
		if(ticketList == null) {
			System.out.println("is null");
		}
		for(Ticket t : ticketList) {
			System.out.println(t);
		}
	}
}
