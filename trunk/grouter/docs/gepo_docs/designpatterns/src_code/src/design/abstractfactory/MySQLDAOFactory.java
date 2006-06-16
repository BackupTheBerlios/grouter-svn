package design.abstractfactory;

import java.sql.Connection;

public class MySQLDAOFactory extends DAOFactory {

	
/**
 * @label returns interface
 */
/*#design.abstractfactory.AccountDAO Dependency_Link*/
/*#design.abstractfactory.CustomerDAO Dependency_Link1*/


public static final String DRIVER = "mysql.JdbcDriver";

	public static final String DBURL = "jdbc://localhost:123/mysqldb";

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
