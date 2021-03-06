package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.service.UserService;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = -2305426300254390114L;
	private static UserService userService = new UserService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		try {
			PrintWriter out = resp.getWriter();

			List<User> users = userService.getAllUsers();
			String usersJSON = mapper.writeValueAsString(users);
			resp.setStatus(200);
			out.write(usersJSON);
			
		} catch (NumberFormatException nfe) {
//			log.error(nfe.getMessage());
			resp.setStatus(400);
		} catch (Exception e) {
			e.printStackTrace();
//		log.error(e.getMessage());
			resp.setStatus(500);
		}

	}

}
