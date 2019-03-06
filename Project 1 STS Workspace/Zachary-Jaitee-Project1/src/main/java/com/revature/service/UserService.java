package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.UserDAO;
import com.revature.models.User;

public class UserService {
	
	private UserDAO dao = new UserDAO();
	
	public ArrayList<User> getAllUsers(){
		return dao.getAll();
	}
	
	

}
