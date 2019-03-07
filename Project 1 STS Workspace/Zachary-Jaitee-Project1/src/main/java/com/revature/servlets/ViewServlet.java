package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		String uri = req.getRequestURI();
		System.out.println(uri);
		if(uri.equalsIgnoreCase("/Zachary-Jaitee-Project1/login.view")) {
			req.getRequestDispatcher("partials/login.html").forward(req, resp);			
		}else {
			resp.setStatus(500);
		}	
	}
}
