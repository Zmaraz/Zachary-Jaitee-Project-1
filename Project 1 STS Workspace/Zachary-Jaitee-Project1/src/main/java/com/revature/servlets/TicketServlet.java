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
import com.fasterxml.jackson.databind.util.JSONPObject;
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
				
				System.out.println("in ticket try");
				ArrayList<Ticket> tickets = service.getAll();
				for(Ticket t : tickets) {
//					System.out.println("in ticket try for loop");
					System.out.println(t);
				}
				
				String temp = "";
				JSONPObject myObject = new JSONPObject("tickets", tickets);
				resp.setHeader("Content-Type", "application/json");
				mapper.writeValue(pw, tickets);
//				pw.write(mapper.writeValueAsString(myObject));
				log.info("tickets have been sent");
//				pw.write("<h1>TESTING</<h1>");
//				for(Ticket t : tickets) {
//					temp += t;
//					pw.write(temp);
//				}
//				pw.write(s);(tickets);
				
				
			}else if(principal.getRole().equalsIgnoreCase("employee")) {
				
				ArrayList<Ticket> tickets = service.getByAuthorId(Integer.parseInt(principal.getId()));
				
			}
		}catch(JsonProcessingException jpe) {
			jpe.printStackTrace();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
