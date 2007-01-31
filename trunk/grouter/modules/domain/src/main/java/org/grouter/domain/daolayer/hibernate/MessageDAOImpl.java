package org.grouter.domain.daolayer.hibernate;

import org.hibernate.*;
import org.grouter.domain.entitylayer.Message;
import org.grouter.domain.daolayer.MessageDAO;

import java.util.*;

/**
 * Hibernate-specific implementation of the <tt>MessageDAOImpl</tt>
 * non-CRUD data access object.
 *
 */
public class MessageDAOImpl extends GenericHibernateDAO<Message, String> implements MessageDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public MessageDAOImpl()
    {
        super(Message.class);
    }

    public MessageDAOImpl(Session session)
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
        String hsql = "from Message obj where obj.node.id = :nodeid";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return (List<Message>) qr.setParameter("nodeid",nodeId).list();
    }

    /**
     * Makes transient message persisted.
     *
     * @param message
     * @return the persisted message with an id
     */
    public Message createMessage(Message message)
    {
        return save(message);
    }

    public void setSession(Session s)
    {
        //session = s;
        session = getSessionFactory().getCurrentSession();
    }

}
