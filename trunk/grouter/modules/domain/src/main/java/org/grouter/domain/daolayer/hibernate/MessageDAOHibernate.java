package org.grouter.domain.daolayer.hibernate;

import org.hibernate.*;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.entities.Message;

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
        super(Message.class);
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
        return (List<Message>) qr.setParameter("nodeId",nodeId).list();
    }

    /**
     * Makes transient message persisted.
     *
     * @param message
     * @return the persisted message with an id
     */
    public Message createMessage(Message message)
    {
        return saveOrUpdate(message);
    }

    public void setSession(Session s)
    {
        session = s;
    }

}
