package org.grouter.domain;

public class Receiver
{
	/**
	 * @directed true
	 */
	private Address address;
	private String id;
	private String name;
	private Message message;

	public Receiver()
	{
	}

	public Message getMessage()
	{
		return message;
	}

	public void setMessage(Message message)
	{
		this.message = message;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public Receiver(String name)
	{
		this.name = name;
	}

	private void setId(String id)
	{
		this.id = id;
	}
}
