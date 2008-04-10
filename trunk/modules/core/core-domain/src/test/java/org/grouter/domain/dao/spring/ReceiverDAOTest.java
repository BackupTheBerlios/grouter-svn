package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.entities.Receiver;
import org.hibernate.LazyInitializationException;

import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class ReceiverDAOTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(ReceiverDAOTest.class);

    @Override
    public void testFindById()
    {
        Receiver found = receiverDAO.findById(RECEIVER_ID);
        assertNotNull(found.toString());
        assertEquals("A receiver 1", found.getName());
    }

    @Override
    public void testSave()
    {
        Receiver receiver = new Receiver();
        receiver.setName("aname");
        receiverDAO.save(receiver);
        flushSession();
        Long id = receiver.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM receiver WHERE id = ?", new Object[]{id});
        assertEquals("aname", map.get("name"));
    }

    @Override
    public void testLazyCollections()
    {
        Receiver found = receiverDAO.findById(RECEIVER_ID);
        assertNotNull(found.toString());
        endTransaction();
        try
        {
            found.getMessages().toString();
            fail("Should not load messages eagerly");
        } catch (LazyInitializationException e)
        {
            //expected
        }catch (Exception e)
        {
            //expected
        }
    }

    @Override
    public void testDelete()
    {
        // test that messages are not deleted when a receiver is deleted
        //assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = '" + ROUTER_ID + "'"));

        try
        {
            receiverDAO.delete(RECEIVER_ID);
            flushSession();
        } catch (Exception e)
        {
            //expected
        }
    }

}