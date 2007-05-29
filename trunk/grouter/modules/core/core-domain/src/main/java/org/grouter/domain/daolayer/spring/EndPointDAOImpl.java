package org.grouter.domain.daolayer.spring;

import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.daolayer.EndPointDAO;
import org.hibernate.Session;



public class EndPointDAOImpl extends GenericHibernateDAO<EndPoint, String> implements EndPointDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public EndPointDAOImpl()
    {
        super(EndPoint.class);
    }

    public EndPointDAOImpl(Session session)
    {
        super(EndPoint.class, session);
    }
}