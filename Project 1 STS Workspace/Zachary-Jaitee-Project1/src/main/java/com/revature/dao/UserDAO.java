package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.models.enums.UserRole;

import util.ConnectionFactory;

public class UserDAO implements DAO<User>{
	private static Logger log = Logger.getLogger(UserDAO.class);
	
	public User getByCredetials(String username, String password){
		log.info("in UserDAO.getByCredentials()");
		
		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id WHERE ers_username = ? AND ers_password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(rs.getInt("ers_user_id"));
				user.setUsername(rs.getString("ers_username"));
				user.setPassword(rs.getString("ers_password"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(UserRole.valueOf(rs.getString("user_role")));
			}
			
			
		}catch(SQLException e) {
			log.warn("SQLException caught in UserDAO.getByCredentials()");
			e.printStackTrace();
		}
		
		if(user.getUserId() == 0)
			return null;
		return user;
	}
	
	// Basic CRUD methods from DAO -----------------------------------------------------------
	
	@Override
	public ArrayList<User> getAll() {
		log.info("in UserDAO.getAll()");
		
		ArrayList<User> users = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			System.out.println("connection made!");
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
				
				users.add(temp);
			}
			
		}catch(SQLException e) {
			log.warn("SQLException caught in UserDAO.getAll()");
			e.printStackTrace();
		}
		
		return users;
	}

	@Override
	public User getById(int userId) {
		log.info("in UserDAO.getById()");
		
		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users u JOIN ers_user_roles r ON u.user_role_id = r.ers_user_role_id WHERE ers_user_id = ?");
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user.setUserId(rs.getInt("ers_user_id"));
				user.setUsername(rs.getString("ers_username"));
				user.setPassword(rs.getString("ers_password"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setEmail(rs.getString("user_email"));
				user.setRole(UserRole.valueOf(rs.getString("user_role")));
			}
			
			
		}catch(SQLException e) {
			log.warn("SQLException caught in UserDAO.getById()");
			e.printStackTrace();
		}
		
		if(user.getUserId() == 0)
			return null;
		return user;
	}

	
	@Override
	public User add(User newUser) {
		log.info("in UserDAO.add()");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			conn.setAutoCommit(false);
			
			String[] keys = new String[1];
			keys[0] = "ers_user_id";
			
			//the value for the Role_id is set to 1, for employee, as manager users must be created on the DB side
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_users VALUES (0, ?, ?, ?, ?, ?, 1)", keys);
			pstmt.setString(1, newUser.getUsername());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getFirstName());
			pstmt.setString(4, newUser.getLastName());
			pstmt.setString(5, newUser.getEmail());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsInserted != 0) {
				while(rs.next()) {
					newUser.setUserId(rs.getInt(1));
				}
				
				conn.commit();
			}
			
		}catch(SQLIntegrityConstraintViolationException sie){
			log.warn("SQLIntegrityConstraintViolationException caught in UserDAO.add()");
		}catch(SQLException e) {
			log.warn("SQLException caught in UserDAO.add()");
			e.printStackTrace();
		}
		
		if(newUser.getUserId() == 0) {
			return null;
		}
		return newUser;
	}

//	@Override
	public ArrayList<User> update(User updatedObj) {
		log.info("in UserDAO.update()");
		
		return null;
	}

	@Override
	public boolean delete(int id) {
		log.info("in UserDAO.delete()");
		
		return false;
	}
}
