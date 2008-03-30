package design.abstractfactory;

import java.util.Collection;

public interface AccountDAO {
	 public int insertAccount();
	  public boolean deleteAccount();
	  public boolean updateAccount();
	  public Collection findAll();

}
