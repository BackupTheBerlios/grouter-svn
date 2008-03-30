package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.RouterDAO;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.hibernate.LazyInitializationException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        router.setId("testid");
        router.setDisplayName("a name");
        router.setDescription("a descr");
        router.setHomePath("/file");


        Node node = new Node();
        node.setId("testnode");
        node.setDisplayName("a node name");
        Set<Node> nodes = new HashSet<Node>();
        nodes.add(node);

        routerDAO.save(router);
        log.debug("Saved instance with id : " + router.getId());

        flushSession();


        String id = router.getId();
        Map motorDealerMap = jdbcTemplate.queryForMap("SELECT * FROM router WHERE id = ?",
                new Object[]{id});
        assertEquals("a name", motorDealerMap.get("displayname"));
        assertEquals("/file", motorDealerMap.get("homepath"));
        assertEquals("jndi", motorDealerMap.get("settings_fk"));
    }

    public void testLazyCollections()
    {
        Router router = routerDAO.findById(ROUTER_ID);
        assertNotNull(router);
        endTransaction();
        try
        {
            router.getNodes().toString();
        }
        catch (LazyInitializationException lie)
        {
            //expected
        }

    }

    public void testFindById()
    {
        Router router = routerDAO.findById(ROUTER_ID);
        assertNotNull(router);
        assertEquals("ROUTER_TEST", router.getDisplayName());
    }

    public void testFindById2()
    {
        List<Router> routers = routerDAO.findAllDistinct();
        assertNotNull(routers);
    }

    public void testFindAndJoin()
    {
        Router router = routerDAO.findById(ROUTER_ID, "nodes");
        assertNotNull(router);
        endTransaction();
        try
        {
            router.getNodes().toString();
            assertEquals(2, router.getNodes().size());
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should have been loaded");
        }
    }


    public void testDelete()
    {
        // A delete should cascde into node and into a nodes enpoints... a very dangerous operation.
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM router WHERE id = '" + ROUTER_ID + "'"));
        assertEquals(2, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE router_fk = '" + ROUTER_ID + "'"));
        routerDAO.delete(ROUTER_ID);
        flushSession();
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM router WHERE id = '" + ROUTER_ID + "'"));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE router_fk = '" + ROUTER_ID + "'"));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM endpoint"));
    }

}
