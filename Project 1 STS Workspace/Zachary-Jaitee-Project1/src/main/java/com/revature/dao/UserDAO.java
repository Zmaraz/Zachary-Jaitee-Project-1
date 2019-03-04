package com.revature.dao;

import java.util.List;

import com.revature.models.User;

public class UserDAO implements DAO<User>{

	public List<User> getByCredetials(String username, String password){
		return null;
	}
	
	// Basic CRUD methods from DAO -----------------------------------------------------------
	
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User add(User obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User updatedObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
