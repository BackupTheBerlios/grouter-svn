package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.RouterDAO;
import org.grouter.domain.entities.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class RouterDAOTest extends AbstractDAOTests
{
    private RouterDAO routerDAO;
    private static Log log = LogFactory.getLog(RouterDAOTest.class);


    public void setRouterDAO(RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }

    public void testSave()
    {
        Router router = new Router();
        router.setId( "testid" );
        router.setName("a name");

        Node node = new Node();
        node.setId( "testnode" );
        node.setName("a node name");
        Set<Node> nodes = new HashSet<Node>();
        nodes.add( node );

        router.setNodes( nodes);

        routerDAO.save(router);
        log.debug("Saved instance with id : " + router.getId());

        flushSession();

        String id = router.getId();
        Map motorDealerMap = jdbcTemplate.queryForMap("SELECT * FROM router WHERE id = ?",
                new Object[]{id});
        assertEquals("a name", motorDealerMap.get("name"));
    }

    public void testLazy()
    {
        Router router =  routerDAO.findById(ROUTER_ID);

//        message.getReceivers();
        assertNotNull( router );

        endTransaction();


        try
        {
            router.getNodes().toString();
            assertEquals( 1, router.getNodes().size() );

        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }

    }


    /** Should not be able to delete a router...
    public void testDelete() throws Exception
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM message WHERE id = '" + MESSAGE_ID + "'"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE id = '" + NODE_ID + "'"));

        messageDAO.delete( new Message( MESSAGE_ID ) );
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

     */
}
