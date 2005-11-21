package org.grouter.domain;

import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;

public class Message
{
	private String id;
	private String message;
	/**
	 * No duplicate elements and the ordering is not relevant for us -> Set
	 */
	private Set<Receiver> receivers = new HashSet();
	private Sender sender;
	private Timestamp creationTime;

	public Message()
	{
	}

	public Timestamp getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime)
	{
		this.creationTime = creationTime;
	}

	public Message(String message, Set<Receiver> receivers, Sender sender)
	{
		/*
		 * if (message == null || receivers == null || sender == null) { throw new IllegalArgumentException("Non null
		 * parameters not allowed for this domain object."); }
		 */
		this.message = message;
		this.receivers = receivers;
		this.sender = sender;
	}

	public String getId()
	{
		return id;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * Prevent direct access to this Set.
	 * 
	 * @return
	 */
	protected Set<Receiver> getReceivers()
	{
		return receivers;
	}

	/**
	 * Prevent direct access to this Set.
	 * 
	 * @param receivers
	 */
	protected void setReceivers(Set<Receiver> receivers)
	{
		this.receivers = receivers;
	}

	public Sender getSender()
	{
		return sender;
	}

	public void setSender(Sender sender)
	{
		this.sender = sender;
	}

	private void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
	 * 
	 * @param receiver
	 */
	public void addToReceivers(Receiver receiver)
	{
		this.getReceivers().add(receiver);
		receiver.setMessage(this);
	}

	/**
	 * Enforce bi-directionality in Java - nothing special but needs to be done (in cotrast to ejb cmp).
	 * 
	 * @param receiver
	 */
	public void removeFromReceivers(Receiver receiver)
	{
		this.getReceivers().remove(receiver);
		receiver.setMessage(null);
	}

}
