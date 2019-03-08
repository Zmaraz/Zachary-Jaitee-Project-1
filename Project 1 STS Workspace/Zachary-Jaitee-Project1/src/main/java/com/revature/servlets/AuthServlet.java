package com.revature.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.util.JWTConfig;
import com.revature.util.JWTGenerator;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	UserService service = new UserService();
	public static User user = new User();
	
	/**
	 * The doGet method handles login verification
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		try {
			user = service.getByCredentials(username, password);
			resp.setStatus(200);
			resp.addHeader(JWTConfig.HEADER, JWTConfig.PREFIX + JWTGenerator.createJwt(user));
			
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(InvalidInputException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		newUser.setEmail(req.getParameter("inputEmail"));
		
		
//			newUser = service.add(newUser);
			System.out.println("Parameters: ");
			System.out.println(req.getParameter("username"));
			System.out.println(req);
			
			Map<String, String[]> params = req.getParameterMap();
	        for (Map.Entry<String,String[]> entry : params.entrySet()) {
	        	System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()[0]); 
	        }
	        try {
				if(service.getByCredentials(req.getParameter("username"), req.getParameter("inputPassword")) == null) {
					System.out.println("is null");
				}
				else
					System.out.println("not null");
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	}

}
