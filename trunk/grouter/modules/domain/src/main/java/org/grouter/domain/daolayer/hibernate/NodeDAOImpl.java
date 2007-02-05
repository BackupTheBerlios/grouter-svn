package org.grouter.domain.daolayer.hibernate;

import org.grouter.domain.entities.Node;
import org.grouter.domain.daolayer.NodeDAO;
import org.hibernate.Session;


public class NodeDAOImpl extends GenericHibernateDAO<Node, String> implements NodeDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public NodeDAOImpl()
    {
        super(Node.class);
    }

    public NodeDAOImpl(Session session)
    {
        super(Node.class, session);
    }


    public void setSession(Session s)
    {
        session = getSessionFactory().getCurrentSession();
    }

}
