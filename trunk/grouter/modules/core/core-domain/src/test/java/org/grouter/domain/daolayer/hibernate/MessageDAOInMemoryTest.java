package org.grouter.domain.daolayer.hibernate;

/**
 * Unit test MessageDAO interface.
 * 
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.entities.*;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.spring.MessageDAOHibernate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageDAOInMemoryTest extends AbstractInMemoryDataInitializationTests
{
    MessageDAOHibernate messageDAOHibernate;
    private static Log log = LogFactory.getLog(MessageDAOInMemoryTest.class);



    public void testSaveMessage()
    {
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Sender sender = new Sender("A test sender");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        message.setCreationTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        sender.addToMessages(message);
        messageDAO.save(message);
        log.debug("Saved instance with id : " + message.getId());

        Message result = messageDAO.findById(message.getId());
        log.debug("Sender : " + result.getSender().getName());

        assertEquals("Persisted should be equals to found instance", result.getId(), message.getId());
    }

    public void testFinder()
    {
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Message resultNotFound = messageDAO.findById("-123");
        log.debug("### Sender : " + resultNotFound.getId());
        assertNull(resultNotFound);
        //log.debug("Sender : " + resultNotFound);

    }
}