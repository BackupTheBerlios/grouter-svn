package design.abstractfactory;

import java.util.Collection;

public interface CustomerDAO 
{
	 public int insertCustomer();
	  public boolean deleteCustomer();
	  public boolean updateCustomer();
	  public Collection findAll();
}
