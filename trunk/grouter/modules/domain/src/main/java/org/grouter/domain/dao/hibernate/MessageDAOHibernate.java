package org.grouter.domain.dao.hibernate;

import org.hibernate.*;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.Message;

import java.util.*;

/**
 * Hibernate-specific implementation of the <tt>MessageDAO</tt>
 * non-CRUD data access object.
 *
 */
public class MessageDAOHibernate extends GenericHibernateDAO<Message, String> implements MessageDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public MessageDAOHibernate()
    {
    }

    public MessageDAOHibernate(Session session)
    {
        super(Message.class, session);
    }

    @SuppressWarnings("unchecked")
    public List<Message> findConcrete(Class concreteClass)
    {
        return getSession().createCriteria(concreteClass).list();
    }

    
    public List<Message> findMessagesForNode(String nodeId)
    {
        String hsql = "from Message obj where obj.nodeId = : nodeId";
        Query qr = getSession().createQuery(hsql);
        List<Message> result = (List<Message>) qr.setParameter("nodeId",nodeId).list();
        return result;
    }

}
