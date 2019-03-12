package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Principal;
import com.revature.models.Ticket;
import com.revature.service.TicketService;

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TicketServlet.class);
	private TicketService service = new TicketService();
	private Ticket ticket = new Ticket();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		
		
		try {
			PrintWriter pw = resp.getWriter();
			Principal principal = (Principal) req.getAttribute("principal");
			
			if(principal.getRole().equalsIgnoreCase("manager")) {
				
				System.out.println("in manager ticket try for id: " + principal.getId());
				ArrayList<Ticket> tickets = service.getAll();
				for(Ticket t : tickets) {
					System.out.println(t);
				}
				
				resp.setHeader("Content-Type", "application/json");
				resp.setHeader("UserRole", "manager");
				mapper.writeValue(pw, tickets);
				log.info("tickets have been sent");

				
				
			}else if(principal.getRole().equalsIgnoreCase("employee")) {
				
				System.out.println("in employee ticket try for id: " + principal.getId());
				ArrayList<Ticket> tickets = service.getByAuthorId(Integer.parseInt(principal.getId()));
				for(Ticket t : tickets) {
					System.out.println(t);
				}
				
				resp.setHeader("Content-Type", "application/json");
				resp.setHeader("UserRole", "employee");
				mapper.writeValue(pw, tickets);
				log.info("tickets have been sent");
				
			}
		}catch(JsonProcessingException jpe) {
			jpe.printStackTrace();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("in TicketServlet doPost()");
		
		ObjectMapper mapper = new ObjectMapper();
		String[] ticketData = null;
		
		try {
			ticketData = mapper.readValue(req.getInputStream(), String[].class);
			for(String s : ticketData) {
				log.info(s);
			}
			
			ticket.setAuthorId(Integer.parseInt(ticketData[0]));
			ticket.setAmount(Double.parseDouble(ticketData[1]));
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}








































