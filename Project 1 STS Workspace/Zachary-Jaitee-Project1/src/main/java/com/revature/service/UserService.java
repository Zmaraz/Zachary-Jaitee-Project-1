package com.revature.service;

import java.util.ArrayList;

import com.revature.dao.UserDAO;
import com.revature.models.User;

public class UserService {
	
	private UserDAO userDao = new UserDAO();
	
	public User getByCredentials(String username, String password) {
		return userDao.getByCredetials(username, password);
	}
	
	public ArrayList<User> getAllUsers(){
		return userDao.getAll();
	}
	
	public User getById(int userId) {
		return userDao.getById(userId);
	}
	
	public User add(User newUser) {
		return userDao.add(newUser);
	}
	
	// UserDAO.update() is unimplemented
	public ArrayList<User> update(User UpdatedUser){
		return userDao.update(UpdatedUser);
	}
	
	// UserDAO.delete is unimplemented
	public boolean delete(int userId) {
		return userDao.delete(userId);
	}

}
