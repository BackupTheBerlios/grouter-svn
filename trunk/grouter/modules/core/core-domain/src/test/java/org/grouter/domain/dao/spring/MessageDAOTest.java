package org.grouter.domain.dao.spring;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.hibernate.LazyInitializationException;
import org.hibernate.stat.SecondLevelCacheStatistics;

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
        message.setNode(nodeDAO.findById(NODE_ID));
        messageDAO.save(message);
        log.debug("Saved instance with id : " + message.getId());

        flushSession();

        String id = (String) message.getId();

        Map map = jdbcTemplate.queryForMap("SELECT * FROM message WHERE id = ?",
                new Object[]{id});
        assertEquals("A test message", map.get("content"));

    }

    @Override
    public void testLazyCollections()
    {
        Message message = messageDAO.findById(MESSAGE_ID);
        assertNotNull(message);
        endTransaction();
        try
        {
            message.getReceivers().toString();
            assertEquals(1, message.getReceivers().size());
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

        messageDAO.delete(MESSAGE_ID);
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


    public void testTestCacheCriteria()
    {
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 10; i++)
        {
            System.out.println(Message.class.getCanonicalName());
            SecondLevelCacheStatistics settingsStatistics = sessionFactory.getStatistics().getSecondLevelCacheStatistics(Message.class.getCanonicalName());
            assertEquals(0, settingsStatistics.getMissCount());
            assertEquals(0, settingsStatistics.getHitCount());
            stopWatch.start();
            messageDAO.findById(MESSAGE_ID);
            stopWatch.stop();
            System.out.println("time : " + stopWatch.getTime());
            stopWatch.reset();
            flushSession();
            System.out.println(settingsStatistics);
            //System.out.println(sessionFactory.getStatistics().getSecondLevelCacheStatistics("org.grouter.domain.entities.Message"));
        }
    }


    public void testTestCache()
    {
        long numberOfMessages = jdbcTemplate.queryForInt("SELECT count(*) FROM message ");
        System.out.println("Number of rows :" + numberOfMessages);
        final String cacheRegion = Message.class.getCanonicalName();
        SecondLevelCacheStatistics settingsStatistics = sessionFactory.getStatistics().getSecondLevelCacheStatistics(cacheRegion);
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < 10; i++)
        {
            stopWatch.start();
            messageDAO.findAllMessages();
            stopWatch.stop();
            System.out.println("Query time : " + stopWatch.getTime());
            assertEquals(0, settingsStatistics.getMissCount());
            assertEquals(numberOfMessages * i, settingsStatistics.getHitCount());
            stopWatch.reset();
            System.out.println(settingsStatistics);
            endTransaction();

            // spring creates a transaction when test starts - so we first end it then start a new in the loop
            startNewTransaction();
        }
    }

}
