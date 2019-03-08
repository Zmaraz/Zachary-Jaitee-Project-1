package com.revature.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.exceptions.ConflictingUserException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.util.JWTConfig;
import com.revature.util.JWTGenerator;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AuthServlet.class);
	private UserService service = new UserService();
	private User user = new User();
	
	/**
	 * The doGet method handles login verification
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	/**
	 * The doPut method handles registration verification
	 */

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String[] credentials = null;
		
		try {
			credentials = mapper.readValue(req.getInputStream(), String[].class);
			System.out.println(credentials[0]);
			if(credentials[0].equals("login")) {
				log.info("logging in");
				user = service.getByCredentials(credentials[1], credentials[2]);
				
				resp.setStatus(200);
				resp.addHeader(JWTConfig.HEADER, JWTConfig.PREFIX + JWTGenerator.createJwt(user));
				
			} else if(credentials[0].equals("register")){
			log.info("in registration");
			user.setFirstName(credentials[1]);
			user.setLastName(credentials[2]);
			user.setUsername(credentials[3]);
			user.setPassword(credentials[4]);
			user.setEmail(credentials[5]);
		
		
				user = service.add(user);
				resp.setStatus(200);
				resp.addHeader(JWTConfig.HEADER, JWTConfig.PREFIX + JWTGenerator.createJwt(user));
			}
		}catch (ConflictingUserException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				resp.setStatus(400);
			} catch (InvalidInputException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				resp.setStatus(400);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setStatus(401);
			} catch (MismatchedInputException mie) {
				log.error(mie.getMessage());
				resp.setStatus(400);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
				resp.setStatus(500);
				return;
			}
			
			
			/****might not need this loop through the params here but maybe later??**/
//			Map<String, String[]> params = req.getParameterMap();
//	        for (Map.Entry<String,String[]> entry : params.entrySet()) {
//	        	System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()[0]); 
//	        }
//			
			
	        
	}
}
