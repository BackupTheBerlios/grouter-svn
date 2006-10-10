package org.grouter.domain.dao.ejb3;

import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.dao.hibernate.SystemUserDAOHibernate;


/**
 * All DAOs for ejb based access are declared in here.
 *
 * @author Georges Polyzois
 */
public class Ejb3DAOFactory extends DAOFactory
{
  
    // Add your DAO interfaces below here
    public MessageDAO getMessageDAO()
    {
        return new MessageDAOBean();
    }

    public SystemUserDAO getSystemUserDAO()
    {
        return new SystemUserDAOHibernate();
    }
}
