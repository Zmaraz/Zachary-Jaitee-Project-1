package com.revature.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.dao.UserDAO;
import com.revature.exceptions.ConflictingUserException;
import com.revature.models.User;

public class UserService {
	private static Logger log = Logger.getLogger(UserService.class);
	private UserDAO userDao = new UserDAO();
	
	public User getByCredentials(String username, String password) {
		log.info("in UserService.getByCredentials()");
		if(!username.equals("") && !password.equals("")) {
			return userDao.getByCredetials(username, password);
		}
		else
			return null;
	}
	
	public ArrayList<User> getAllUsers(){
		log.info("in UserService.getAllUsers()");
		return userDao.getAll();
	}
	
	public User getById(int userId) {
		log.info("in UserService.getById()");
		if(userId <= 0)
			return null;
		return userDao.getById(userId);
	}
	
	// potentially create new DAO method OR alter DAO add method to make seperate query to validate username and email are unique
	// in order to not have to make seprate DAO calls and make seperate connections
	public User add(User newUser) throws ConflictingUserException{
		log.info("in UserService.add()");
		ArrayList<User> userList = userDao.getAll();
		
		for(User u : userList) {
			if(newUser.getUsername().equals(u.getUsername()) || newUser.getEmail().equals(u.getEmail())) {
				log.warn("ConflictingUserException thrown in UserService.add()");
				throw new ConflictingUserException("Username or Email already taken");
			}
		}
		return userDao.add(newUser);
	}
	
	// UserDAO.update() is unimplemented
	public ArrayList<User> update(User UpdatedUser){
		log.info("in UserService.update()");
		return userDao.update(UpdatedUser); // will currently always return null
	}
	
	// UserDAO.delete is unimplemented
	public boolean delete(int userId) {
		log.info("in UserService.delete()");
		return userDao.delete(userId); // will currently always return false
	}
}