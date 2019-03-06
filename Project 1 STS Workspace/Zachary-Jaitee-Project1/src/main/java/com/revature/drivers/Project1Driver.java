package com.revature.drivers;

import java.util.ArrayList;

import com.revature.dao.TicketDAO;
import com.revature.models.Ticket;
import com.revature.models.enums.ReimbursementStatus;

public class Project1Driver {
	public static void main(String[] args) {
		System.out.println("Hello Project 1!");
		
		TicketDAO ticketDao = new TicketDAO();
		ArrayList<Ticket> tlist = new ArrayList<>();
		
		System.out.println("Original list:");
		
		Ticket t1 = ticketDao.getById(23);
		System.out.println(t1);
		t1.setStatus(ReimbursementStatus.DENIED);
		t1.setResolverId(25);
		
//		Ticket t2 = ticketDao.getById(42);
//		System.out.println(t2);
//		t2.setStatus(ReimbursementStatus.DENIED);
//		t2.setResolverId(25);
		
		tlist.add(t1);
//		tlist.add(t2);
		
		
		System.out.println("\n");
		System.out.println("New List:");
		ArrayList<Ticket> updatedList = ticketDao.update(tlist);
		
		for(Ticket t : updatedList) {
			System.out.println(t);
		}
	}
}
