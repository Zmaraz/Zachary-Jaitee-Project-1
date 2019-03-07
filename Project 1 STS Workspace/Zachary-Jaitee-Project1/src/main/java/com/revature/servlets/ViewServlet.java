package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.view")
public class ViewServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		String uri = req.getRequestURI();
		System.out.println(uri);
		if(uri.equalsIgnoreCase("login.view")) {
			req.getRequestDispatcher("partials/login").forward(req, resp);			
		}else {
			resp.setStatus(500);
		}
		
	}

}
