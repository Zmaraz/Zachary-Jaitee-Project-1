package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.Ticket;
import com.revature.models.enums.ReimbursementStatus;
import com.revature.models.enums.ReimbursementType;

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
	public Ticket getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket add(Ticket obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket update(Ticket updatedObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
