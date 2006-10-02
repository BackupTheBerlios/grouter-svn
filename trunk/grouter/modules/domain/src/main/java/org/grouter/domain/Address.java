package org.grouter.domain;

import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Domain class.
 * @Author Georges Polyzois
 */
@Entity
public class Address
{
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String address;
    @ManyToOne
    private Country country;

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
