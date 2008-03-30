package design.abstractfactory;

abstract class DAOFactory 
{
	  public static final int MYSQL = 1;
	  public static final int ORACLE = 2;

	  // Concrete factories need to implement this.
	  public abstract CustomerDAO getCustomerDAO();
	  public abstract AccountDAO getAccountDAO();

	  public static DAOFactory getDAOFactory( int whichFactory) {
	    switch (whichFactory) {
	      case ORACLE    : 
	          return new OracleDAOFactory();      
	      case MYSQL    : 
	          return new MySQLDAOFactory();
	      default           : 
	          return null;
	    }
	  }
}
