package org.grouter.domain;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.Column;
import java.util.Set;
import java.util.HashSet;

/**
 * Domain class.
 * @Author Georges Polyzois
 */
@Entity
public class Country
{
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String timeZone;
    @OneToMany
    private Set<Address> addresses = new HashSet();

    
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
