package org.grouter.domain.daolayer.ejb3;

import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.daolayer.hibernate.SystemUserDAOHibernate;


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
