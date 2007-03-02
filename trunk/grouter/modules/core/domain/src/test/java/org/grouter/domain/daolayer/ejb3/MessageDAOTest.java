package org.grouter.domain.daolayer.ejb3;

/**
 * Unit test MessageDAOImpl interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.daolayer.hibernate.MessageDAOImpl;
import org.grouter.domain.daolayer.util.AbstractInMemoryDbInitTestData;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageDAOTest extends AbstractInMemoryDbInitTestData
{
    MessageDAOImpl messageDAO;
    private static Log log = LogFactory.getLog(MessageDAOTest.class);



    public void testSaveMessage()
    {
        org.grouter.domain.daolayer.MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Sender sender = new Sender("A test sender");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        message.setCreationTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        sender.addToMessages(message);
        messageDAO.save(message);
        log.debug("Saved instance with id : " + message.getId());

        Message result = messageDAO.findById(message.getId(),false);
        log.debug("Sender : " + result.getSender().getName());

        assertEquals("Persisted should be equals to found instance", result.getId(), message.getId());
    }

    public void testFinder()
    {
        org.grouter.domain.daolayer.MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Message resultNotFound = messageDAO.findById("-123",false);
        log.debug("### Sender : " + resultNotFound.getId());
        assertNull(resultNotFound);
        //log.debug("Sender : " + resultNotFound);

    }
}