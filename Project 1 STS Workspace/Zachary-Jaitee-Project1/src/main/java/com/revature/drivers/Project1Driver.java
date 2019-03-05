package com.revature.drivers;

import java.util.ArrayList;

import com.revature.dao.UserDAO;
import com.revature.models.User;

public class Project1Driver {
	public static void main(String[] args) {
		System.out.println("Hello Project 1!");
		
		UserDAO userDao = new UserDAO();
		
		ArrayList<User> testUserList = userDao.getAll();
		if(testUserList.size() == 0) {
			System.out.println("0 size");
		}
		
		for(User u : testUserList) {
			System.out.println(u.toString());
		}	
		
		User getIdUser = userDao.getById(22);
		System.out.println(getIdUser.toString());
		
		User addMan = new User("Zachacc","zachpass","Zach","Maraz","asdf@thrn.com"); 
		System.out.println(" !!" + addMan);
		userDao.add(addMan);
		
		User test2 = userDao.getByCredetials("Zachacc", "zacss");
		System.out.println(test2.toString());
	}
}
