package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.User;
import com.revature.models.enums.UserRole;

import util.ConnectionFactory;

public class UserDAO implements DAO<User>{

	public List<User> getByCredetials(String username, String password){
		
		return null;
	}
	
	// Basic CRUD methods from DAO -----------------------------------------------------------
	
	@Override
	public ArrayList<User> getAll() {
		
		ArrayList<User> users = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id");
			
			while(rs.next()) {
				User temp = new User();
				temp.setUserId(rs.getInt("ers_user_id"));
				temp.setUsername(rs.getString("ers_username"));
				temp.setPassword(rs.getString("ers_password"));
				temp.setFirstName(rs.getString("user_first_name"));
				temp.setLastName(rs.getString("user_last_name"));
				temp.setEmail(rs.getString("user_email"));
				temp.setRole(UserRole.valueOf(rs.getString("user_role")));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	@Override
	public User getById(int userId) {
		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			PreparedStatement pstmt = conn.prepareCall("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id WHERE ers_user_id = ?");
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return user;
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
