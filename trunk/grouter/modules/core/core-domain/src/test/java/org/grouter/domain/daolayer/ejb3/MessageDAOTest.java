package org.grouter.domain.daolayer.ejb3;

/**
 * Unit test MessageDAOImpl interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.daolayer.spring.MessageDAOImpl;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.DAOFactory;
//import org.grouter.domain.daolayer..AbstractInMemoryDbInitTestData;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageDAOTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(MessageDAOTest.class);
    protected MessageDAOImpl messageDAO;


    public void testSaveMessage()
    {
        MessageDAO messageDAO = (MessageDAO) DAOFactory.getFactory( DAOFactory.FactoryType.EJB3_PERSISTENCE  );
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
        MessageDAO messageDAO = (MessageDAO) DAOFactory.getFactory( DAOFactory.FactoryType.EJB3_PERSISTENCE  );
        Message resultNotFound = messageDAO.findById("-123");
        log.debug("### Sender : " + resultNotFound.getId());
        assertNull(resultNotFound);
        //log.debug("Sender : " + resultNotFound);

    }

   
}