package org.grouter.domain.daolayer.hibernate;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

import java.util.Map;

/**
 * Test the DAO using an external DB which is populated with data before the actual test is run
 * and then restored to a state as per before the tests were ran. All plumibng for rolling back the
 * state is taken care of by Springs  {@link org.springframework.test.AbstractTransactionalDataSourceSpringContextTests}
 * we we are subclassing from in AbstractDAOTests. AbstractDAOTests referes to the sql to be run before
 * any test are created and run.
 * <p/>
 * The only problem with these types of test are that as we go along and add more and more test the tests
 * start taking considerably long time to execute.
 *
 * @author Georges Polyzois
 */
public class MessageDAOTest extends AbstractDAOTests
{
    MessageDAO messageDAO;
    private static Log log = LogFactory.getLog(MessageDAOTest.class);
    private static final String NODEID = "nodeid_1";


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

    public void testLazyRelations()
    {
        Message message =  messageDAO.findById(MESSAGE_ID);

        assertNotNull(message );

        endTransaction();

        try
        {
            message.getReceivers().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }

    }

    public void testDelete() throws Exception
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = " + MESSAGE_ID));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = " + NODEID));

        messageDAO.delete(MESSAGE_ID);
        flushSession();

        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = " + MESSAGE_ID));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = " + NODEID));

    }


    public void testFinder()
    {
        Message entity = messageDAO.findById(MESSAGE_ID);
        assertNotNull(entity);
        assertEquals("A message 1", entity.getContent());
    }
}
