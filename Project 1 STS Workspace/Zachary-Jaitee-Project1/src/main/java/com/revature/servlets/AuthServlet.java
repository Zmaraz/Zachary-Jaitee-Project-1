package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.models.User;
import com.revature.service.UserService;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		
//		User newUser = new User();
//		UserService service = new UserService();
//		
//		newUser.setUsername(req.getParameter("username"));
//		newUser.setPassword(req.getParameter("inputPassword"));
//		newUser.setEmail(req.getParameter("inputEmail"));
		
		
//			newUser = service.add(newUser);
			System.out.println("Parameters: ");
			System.out.println(req.getParameter("username"));
			while(req.getParameterNames().hasMoreElements()) {
				System.out.println(req.getParameterNames().nextElement());
			}
	
		
	}

}
