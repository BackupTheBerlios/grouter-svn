package org.grouter.domain.dao.ejb3;

/**
 * Unit test MessageDAOImpl interface.
 *
 * @author Georges Polyzois
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.spring.MessageDAOImpl;

public class MessageDAOTestFails //extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(MessageDAOTestFails.class);
    protected MessageDAOImpl messageDAO;


    public void fails_testSaveMessage()
    {
        //assertTrue(true);
        /*MessageDAO messageDAO = (MessageDAO) DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE);
        Sender sender = new Sender("A test sender");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        sender.addToMessages(message);
        messageDAO.save(message);
        log.debug("Saved instance with id : " + message.getId());

        Message result = messageDAO.findById( message.getId());
        log.debug("Sender : " + result.getSender().getName());

        assertEquals("Persisted should be equals to found instance", result.getId(), message.getId());*/
    }

    public void fails_testFinder()
    {
        //as sertTrue(true);
        /*
        MessageDAO messageDAO = (MessageDAO) DAOFactory.getFactory(DAOFactory.FactoryType.EJB3_PERSISTENCE);
        Message resultNotFound = messageDAO.findById( -123L );
        log.debug("### Sender : " + resultNotFound.getId());
        assertNull(resultNotFound);
        //log.debug("Sender : " + resultNotFound);
         */
    }


}