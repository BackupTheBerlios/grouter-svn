package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class MessageDAOTest extends AbstractDAOTests
{
    MessageDAO messageDAO;
    private static Log log = LogFactory.getLog(MessageDAOTest.class);


    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public void testSave()
    {
        Sender sender = new Sender("A test sender");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        message.setCreationTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        sender.addToMessages(message);
        messageDAO.save(message);
        log.debug("Saved instance with id : " + message.getId());

        flushSession();

        String id = message.getId();

        Map motorDealerMap = jdbcTemplate.queryForMap("SELECT * FROM message WHERE id = ?",
                new Object[]{id});
        assertEquals("A test message", motorDealerMap.get("content"));

    }

    public void testLazy()
    {                                                                        
        Message message =  messageDAO.findById(MESSAGE_ID);

//        message.getReceivers();
        assertNotNull(message );

        endTransaction();


        try
        {
            message.getReceivers().toString();
            assertEquals( 1, message.getReceivers().size() );

        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }

    }

    public void testDelete() throws Exception
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = '" + MESSAGE_ID + "'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = '" + NODE_ID + "'"));

        messageDAO.delete(MESSAGE_ID);
        flushSession();

        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = '" + MESSAGE_ID + "'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = '" + NODE_ID + "'"));

    }


    public void testFinder()
    {
        Message entity = messageDAO.findById(MESSAGE_ID);
        assertNotNull(entity);
        assertEquals("A message 1", entity.getContent());
    }
}
