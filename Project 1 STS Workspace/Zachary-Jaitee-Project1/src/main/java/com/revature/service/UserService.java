package com.revature.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.dao.UserDAO;
import com.revature.exceptions.ConflictingUserException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;

public class UserService {
	private static Logger log = Logger.getLogger(UserService.class);
	private UserDAO userDao = new UserDAO();
	
	public Boolean checkForUsername(String username) {
		return userDao.checkForUsername(username);
	}
	
	public User getByCredentials(String username, String password) throws UserNotFoundException, InvalidInputException{
		log.info("in UserService.getByCredentials()");
		if(username == null || password == null) throw new InvalidInputException("Empty credentials");
		if(isValid(username) && isValid(password)) {
			 User gottenUser = userDao.getByCredetials(username, password);
			 if(gottenUser == null) {
				 throw new UserNotFoundException("No user with those credentials");
			 }
			 return gottenUser;
		} else {
			throw new InvalidInputException("Empty credentials");
		}
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
	
	private boolean isValid(String value) {
		if(value == null) return false;
		return (value.trim().length() > 1);
	}
	
	public User add(User newUser) throws ConflictingUserException, InvalidInputException{
		log.info("in UserService.add()");
		
		if(isValid(newUser.getUsername()) && 
				isValid(newUser.getEmail()) && 
				isValid(newUser.getFirstName()) &&
				isValid(newUser.getLastName()) &&
				isValid(newUser.getPassword())){
		}else {
			log.warn("InvalidInputException thrown in UserService.add()");
			throw new InvalidInputException("Input is invalid");
		}
		
		ArrayList<User> userList = userDao.getAll();
		for(User u : userList) {
			// potential change: turn || into &&, depending on if the username and email are supposed to be individually unique or compositely unique
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