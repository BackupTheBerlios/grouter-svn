package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.RouterDAO;
import org.grouter.domain.daolayer.spring.GenericHibernateDAO;
import org.grouter.domain.entities.Router;
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

}
