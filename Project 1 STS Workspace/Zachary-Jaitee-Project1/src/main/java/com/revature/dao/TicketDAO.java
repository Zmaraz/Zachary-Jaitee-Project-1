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
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

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
			temp.setTicketDescription(results.getString("reimb_description"));
			
			
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
		
		Ticket ticket = new Ticket();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_reimbursement WHERE reimb_id = ?");
			pstmt.setInt(1, ticketId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ticket.setReimbId(rs.getInt("reimb_id"));
				ticket.setAmount(rs.getDouble("reimb_amount"));
				ticket.setTimeSubmitted(rs.getString("reimb_submitted"));
				ticket.setTimeResolved(rs.getString("reimb_resolved"));
				ticket.setTicketDescription(rs.getString("reimb_description"));
				ticket.setAuthorId(rs.getInt("reimb_author"));
				ticket.setResolverId(rs.getInt("reimb_resolver"));
				
				switch(rs.getInt("reimb_status_id")) {
				case 0:
					ticket.setStatus(ReimbursementStatus.PENDING);
					break;
				case 1:
					ticket.setStatus(ReimbursementStatus.APPROVED);
					break;
				case 2:
					ticket.setStatus(ReimbursementStatus.DENIED);
					break;
				}
				
				switch(rs.getInt("reimb_type_id")) {
				case 1:
					ticket.setType(ReimbursementType.LODGING);
					break;
				case 2:
					ticket.setType(ReimbursementType.TRAVEL);
					break;
				case 3:
					ticket.setType(ReimbursementType.FOOD);
					break;
				case 4:
					ticket.setType(ReimbursementType.OTHER);
					break;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
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
	public ArrayList<Ticket> update(ArrayList<Ticket> updatedTickets) {
		ArrayList<Ticket> newTicketList = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolver = ? WHERE reimb_id = ?";
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for(Ticket t : updatedTickets) {
				pstmt.setInt(1, t.getStatusId());
				pstmt.setInt(2, t.getResolverId());
				pstmt.setInt(3, t.getReimbId());
				
				if(pstmt.executeUpdate() == 0) {
					return null;
				}
				newTicketList.add(t);
			}
			conn.commit();
			return newTicketList;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
