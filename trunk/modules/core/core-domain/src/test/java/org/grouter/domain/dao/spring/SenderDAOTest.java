package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.entities.Receiver;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.dao.SenderDAO;

import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class SenderDAOTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(SenderDAOTest.class);

    @Override
    public void testFindById()
    {
        Sender found = senderDAO.findById(SENDER_ID);
        assertNotNull(found.toString());
        assertEquals("Sender name 1", found.getName());
    }

    @Override
    public void testSave()
    {
        Sender sender = new Sender();
        sender.setName("aname");
        senderDAO.save(sender);
        flushSession();
        Long id = sender.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM sender WHERE id = ?", new Object[]{id});
        assertEquals("aname", map.get("name"));
    }

    @Override
    public void testLazyCollections()
    {
        Sender found = senderDAO.findById(RECEIVER_ID);
        assertNotNull(found.toString());
        endTransaction();
        try
        {
            found.getMessages().toString();
            fail("Should not load messages eagerly");
        } catch (Exception e)
        {
            //expected
        }
    }

    @Override
    public void testDelete()
    {
        try
        {
            senderDAO.delete(SENDER_ID);
            flushSession();
        } catch (Exception e)
        {
            //expected
        }
    }

}