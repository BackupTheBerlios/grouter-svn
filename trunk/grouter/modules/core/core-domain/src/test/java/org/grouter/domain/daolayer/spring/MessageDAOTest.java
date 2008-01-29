package org.grouter.domain.daolayer.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.hibernate.LazyInitializationException;

import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class MessageDAOTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(MessageDAOTest.class);


    @Override
    public void testSave()
    {
        //Sender sender = new Sender("A test sender");
        Message message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setCreationTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        message.setNode( nodeDAO.findById( NODE_ID ) );
        messageDAO.save(message);
        log.debug("Saved instance with id : " + message.getId());

        flushSession();

        String id = (String)message.getId();

        Map map = jdbcTemplate.queryForMap("SELECT * FROM message WHERE id = ?",
                new Object[]{id});
        assertEquals("A test message", map.get("content"));

    }

    @Override
    public void testLazyCollections()
    {                                                                        
        Message message =  messageDAO.findById(MESSAGE_ID);
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

    @Override
    public void testDelete() 
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = '" + MESSAGE_ID + "'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = '" + NODE_ID + "'"));

        messageDAO.delete( new Message( MESSAGE_ID ) );
        flushSession();

        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = '" + MESSAGE_ID + "'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = '" + NODE_ID + "'"));
    }


    @Override
    public void testFindById()
    {
        Message entity = messageDAO.findById(MESSAGE_ID);
        assertNotNull(entity);
    }
}
