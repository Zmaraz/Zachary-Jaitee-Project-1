package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.revature.util.RequestViews;

public class ViewServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ViewServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		
		String uri = req.getRequestURI();
		log.info(uri);
		
		String nextView = RequestViews.process(req);
		
		if(nextView != null) {
			try {
				req.getRequestDispatcher(nextView).forward(req, resp);
			} catch (Exception e) {
				log.error(e.getMessage());
				resp.setStatus(500);
			}
		} else {
			resp.setStatus(401);
		}
			
//		if(uri.equalsIgnoreCase("/Zachary-Jaitee-Project1/login.view")) {
//			req.getRequestDispatcher("partials/login.html").forward(req, resp);			
//		}else {
//			resp.setStatus(500);
//		}	
	}
}
