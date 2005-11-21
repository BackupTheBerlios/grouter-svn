package org.grouter.domain;

/**
 * Created by IntelliJ IDEA. User: geopol Date: 2005-nov-07 Time: 15:30:12 To change this template use File | Settings |
 * File Templates.
 */
public class Address
{
	String id;
	String name;
	String address;
	Country country;

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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

}
