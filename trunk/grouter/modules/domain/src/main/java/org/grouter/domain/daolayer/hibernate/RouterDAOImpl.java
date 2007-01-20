package org.grouter.domain.daolayer.hibernate;

import org.grouter.domain.entities.Router;
import org.grouter.domain.daolayer.RouterDAO;
import org.hibernate.Session;

/**
 * @author Georges
 */
public class RouterDAOImpl extends GenericHibernateDAO<Router, String> implements RouterDAO
{


    protected RouterDAOImpl()
    {
        super(Router.class);
    }

    protected RouterDAOImpl(Class<Router> persistentClass)
    {
        super(persistentClass);
    }

    public RouterDAOImpl(Class<Router> persistentClass, Session session)
    {
        super(persistentClass, session);
    }

    protected void setSession(Session s)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
