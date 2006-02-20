package org.grouter.domain.dao.test;

/**
 * Unit test MessageDAO interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.dao.hibernate.MessageDAOHibernate;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.Sender;
import org.grouter.domain.Message;
import org.grouter.domain.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestMessageDAOHibernate extends TestData
{
    MessageDAOHibernate messageDAOHibernate;
    private static Log log = LogFactory.getLog(TestMessageDAOHibernate.class);



    public void testSaveMessage()
    {
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Sender sender = new Sender("A test sender");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        message.setCreationTime(new java.sql.Timestamp(System.currentTimeMillis()));
        sender.addToMessages(message);
        messageDAO.saveOrUpdate(message);
        log.debug("Saved instance with id : " + message.getId());

        Message result = messageDAO.findById(message.getId(),false);
        log.debug("Sender : " + result.getSender().getName());

        assertEquals("Persisted should be equals to found instance", result.getId(), message.getId());
    }

    public void testFinder()
    {
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Message resultNotFound = messageDAO.findById("-123",false);
        log.debug("### Sender : " + resultNotFound.getId());
        assertNull(resultNotFound);
        //log.debug("Sender : " + resultNotFound);

    }
}