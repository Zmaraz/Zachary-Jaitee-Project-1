package com.revature.drivers;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Ticket;
import com.revature.service.TicketService;

public class Project1Driver {
	public static void main(String[] args) {
//		System.out.println("Hello Project 1!");
		TicketService service = new TicketService();
//		ObjectMapper mapper = new ObjectMapper();
//		
//		
		ArrayList<Ticket> tickets = service.getByAuthorId(2);
////		objectMapper.writeValue(new File("target/car.json"), car);
//		try {
//			String ticketsString = "";
//			for(Ticket t : tickets) {
////				String temp = mapper.writeValue(, new String());
//				ticketsString += temp;
//			}
////			String ticketsString = mapper.writeValueAsString(tickets);
//			System.out.println(ticketsString);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println(tickets);
		System.out.println(tickets.size());
		for(Ticket t : tickets) {
			System.out.println(t);
		}
		
	}
}
