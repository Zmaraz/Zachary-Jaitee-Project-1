package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.Ticket;
import com.revature.models.enums.ReimbursementStatus;
import com.revature.models.enums.ReimbursementType;
import com.revature.models.enums.UserRole;

import oracle.jdbc.OracleTypes;
import util.ConnectionFactory;

public class TicketDAO implements DAO<Ticket>{
	
	
	public ArrayList<Ticket> ticketMaker(ResultSet results) throws SQLException{
		ArrayList<Ticket> ticketList = new ArrayList<>();
		
		while(results.next()) {
			System.out.println("in ticketmaker");
			
			Ticket temp = new Ticket();
			temp.setReimbId(results.getInt("reimb_id"));
			temp.setAuthorId(results.getInt("author_id"));
			temp.setAmount(results.getDouble("reimb_amount"));			
			temp.setStatus(ReimbursementStatus.valueOf(results.getString("reimb_status")));
			temp.setType(ReimbursementType.valueOf(results.getString("reimb_type")));
			temp.setResolverId(results.getInt("resolver_id"));
			
			
			if(results.getString("reimb_resolved") == null) {
				temp.setTimeResolved("N/A");
			}else {
				temp.setTimeResolved(results.getString("reimb_resolved"));
			}
			
			if(results.getString("reimb_submitted") == null) {
				temp.setTimeSubmitted("N/A");
			}else {
				temp.setTimeSubmitted(results.getString("reimb_submitted"));
			}
			
			ticketList.add(temp);
		}
		return ticketList;
	}
	
	public ArrayList<Ticket> getByAuthorId(int authorId){
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			CallableStatement cstmt = conn.prepareCall("{CALL get_reimbursement_by_id(?, ?)}");
			cstmt.setInt(1, authorId);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			if(!cstmt.execute()) {
				return ticketMaker((ResultSet)cstmt.getObject(2));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public ArrayList<Ticket> getAll() {
		System.out.println("in getall");
		ArrayList<Ticket> tickets = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			CallableStatement cstmt = conn.prepareCall("{CALL get_all_reimbursements(?)}");
			
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			
			//we want .execute to return false
			if(!cstmt.execute()) {
				ResultSet rs = (ResultSet)cstmt.getObject(1);
		
				return ticketMaker(rs);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Ticket getById(int ticketId) {
		return null;
	}

	@Override
	public Ticket add(Ticket obj) {
	
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			conn.setAutoCommit(false);
			
			String [] keys = new String[1];
			keys[0] = "reimb_id";
			//(id, amount, time submitted, time resolved, descr, recipt, author, resolver, status id, type id)
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursement VALUES (0,?,null,null,?,null,?,null,0,?)", keys);
			pstmt.setDouble(1, obj.getAmount());
			pstmt.setString(2, obj.getTicketDescription());
			pstmt.setInt(3, obj.getAuthorId());
			switch(obj.getType()) {
				case LODGING:
					pstmt.setInt(4, 1);
					break;
				case TRAVEL:
					pstmt.setInt(4, 2);
					break;
				case FOOD:
					pstmt.setInt(4, 3);
					break;
				case OTHER:
					pstmt.setInt(4, 4);
					break;
				default:
					return null;
			}
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rowsInserted != 0) {
				while(rs.next()) {
					obj.setReimbId(rs.getInt(1));
				}
				conn.commit();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(obj.getReimbId() == 0) {
			return null;
		}
		return obj;
	}

//	@Override
	public ArrayList<Ticket> update(Ticket updatedObj) {
		
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
