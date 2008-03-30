package design.abstractfactory;

import java.sql.Connection;

/**
 * @label returns interface
 */
/*#design.abstractfactory.CustomerDAO Dependency_Link*/
/*#design.abstractfactory.AccountDAO Dependency_Link1*/

public class OracleDAOFactory extends DAOFactory
{
	  
public static final String DRIVER=
		    "oracle.JdbcDriver";
		  public static final String DBURL=
		    "jdbc://localhost:123/oradb";
		  public static Connection createConnection() {
			  return null;
		  }
		  public CustomerDAO getCustomerDAO() {
		    return new OracleCustomerDAO();
		  }
		  public AccountDAO getAccountDAO() {
		    return new OracleAccountDAO();
		  }
}
