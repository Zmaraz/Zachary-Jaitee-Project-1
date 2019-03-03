package com.revature.models;

import com.revature.models.enums.ReimbursementStatus;
import com.revature.models.enums.ReimbursementType;

//possible subclass possibility. Ticket super class with ResolvedTicket and UnresolvedTicket subclass

/**
 * 
 * @author Zachary, Jaitee
 * The Ticket class represents a single Reimbursement ticket that has been created by a user. Employees can create tickets,
 * and Managers can approve or deny them.
 *
 */
public class Ticket {
	int reimbId;
	int authorId;
	int resolverId;
	double amount;
	String timeSubmitted;
	String timeResolved;
	String ticketDescription;
	ReimbursementStatus status;
	ReimbursementType type;
	
	//reciept img object?
	
	public Ticket() {
		
	}

	/**
	 * constructor for when retrieving a Ticket from the DB
	 * @param reimbId
	 * @param authorId
	 * @param resolverId
	 * @param amount
	 * @param timeSubmitted
	 * @param timeResolved
	 * @param ticketDescription
	 * @param status
	 * @param type
	 */
	public Ticket(int reimbId, int authorId, int resolverId, double amount, String timeSubmitted, String timeResolved,
			String ticketDescription, ReimbursementStatus status, ReimbursementType type) {
		super();
		this.reimbId = reimbId;
		this.authorId = authorId;
		this.resolverId = resolverId;
		this.amount = amount;
		this.timeSubmitted = timeSubmitted;
		this.timeResolved = timeResolved;
		this.ticketDescription = ticketDescription;
		this.status = status;
		this.type = type;
	}

	/**
	 * user does not choose who the resolver is, nor do they choose the status of their ticket upon creation, this is done by the DB, which sets 		it to PENDING by default upon persistence
	 * @param authorId
	 * @param amount
	 * @param ticketDescription
	 * @param type
	 */
	public Ticket(int authorId, double amount, String ticketDescription, ReimbursementType type) {
		super();
		this.authorId = authorId;
		this.amount = amount;
		this.ticketDescription = ticketDescription;
		this.status = ReimbursementStatus.PENDING;
		this.type = type;
	}

	
	// Getters and Setters --------------------------------------------------------------------------------------
	
	public int getReimbId() {
		return reimbId;
	}

	public void setReimbId(int reimbId) {
		this.reimbId = reimbId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getResolverId() {
		return resolverId;
	}

	public void setResolverId(int resolverId) {
		this.resolverId = resolverId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTimeSubmitted() {
		return timeSubmitted;
	}

	public void setTimeSubmitted(String timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	public String getTimeResolved() {
		return timeResolved;
	}

	public void setTimeResolved(String timeResolved) {
		this.timeResolved = timeResolved;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	public ReimbursementStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbursementStatus status) {
		this.status = status;
	}

	public ReimbursementType getType() {
		return type;
	}

	public void setType(ReimbursementType type) {
		this.type = type;
	}

	
	// hashCode(), equals(), and toString() --------------------------------------------------------------------
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + authorId;
		result = prime * result + reimbId;
		result = prime * result + resolverId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((ticketDescription == null) ? 0 : ticketDescription.hashCode());
		result = prime * result + ((timeResolved == null) ? 0 : timeResolved.hashCode());
		result = prime * result + ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (authorId != other.authorId)
			return false;
		if (reimbId != other.reimbId)
			return false;
		if (resolverId != other.resolverId)
			return false;
		if (status != other.status)
			return false;
		if (ticketDescription == null) {
			if (other.ticketDescription != null)
				return false;
		} else if (!ticketDescription.equals(other.ticketDescription))
			return false;
		if (timeResolved == null) {
			if (other.timeResolved != null)
				return false;
		} else if (!timeResolved.equals(other.timeResolved))
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [reimbId=" + reimbId + ", authorId=" + authorId + ", resolverId=" + resolverId + ", amount="
				+ amount + ", timeSubmitted=" + timeSubmitted + ", timeResolved=" + timeResolved
				+ ", ticketDescription=" + ticketDescription + ", status=" + status + ", type=" + type + "]";
	}	
	
	
	
}
