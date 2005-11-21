package org.grouter.domain;

import java.util.Set;
import java.util.HashSet;

/**
 * Class description.
 */
public class Country
{
	String id;
	String name;
	String timeZone;
	Set<Address> addresses = new HashSet();

	public Set<Address> getAddresses()
	{
		return addresses;
	}

	public void setAddresses(Set<Address> addresses)
	{
		this.addresses = addresses;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(String timeZone)
	{
		this.timeZone = timeZone;
	}
}
