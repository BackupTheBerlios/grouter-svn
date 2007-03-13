package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointFileReader;
import org.grouter.domain.entities.EndPointType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class NodeDAOTest extends AbstractDAOTests
{
    NodeDAO nodeDAO;
    private static Log log = LogFactory.getLog(NodeDAOTest.class);


    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }

    public void testFinder()
    {
        Node resultNotFound = nodeDAO.findById(NODE_ID);
        assertNotNull(resultNotFound.toString());
        assertEquals(NODE_ID, resultNotFound.getId());
    }


    /**
     * Build a Node with Enpoints, save and flush. Verify that cascade mappings are correct
     * by pure sql after a flushed session.
     */
    public void testStore()
    {
        Node node = new Node();
        node.setName("A node");

        nodeDAO.save(node);
        flushSession();

        String id = node.getId();
        Map motorDealerMap = jdbcTemplate.queryForMap("SELECT * FROM node WHERE id = ?", new Object[]{id});
        assertEquals("A node", motorDealerMap.get("name"));
    }

    public void testStoreWithEndpoints()
    {
        Node node = new Node();
        node.setName("A node");

        EndPoint inboundEndpoint = new EndPointFileReader();
        inboundEndpoint.setScheduleCron("* * * * * ");
        inboundEndpoint.setUri("file://temp/in");
        inboundEndpoint.setEndPointType(EndPointType.FILE_READER);

        EndPoint outBoundPoint = new EndPointFileReader();
        outBoundPoint.setScheduleCron("* * * * * ");
        outBoundPoint.setUri("file://temp/out");
        outBoundPoint.setEndPointType(EndPointType.FILE_WRITER);

        node.setInBound(inboundEndpoint);
        node.setOutBound(outBoundPoint);

        nodeDAO.save(node);
        flushSession();


        String id = node.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM node WHERE id = ?", new Object[]{id});
        assertEquals("A node", map.get("name"));

        map = jdbcTemplate.queryForMap("SELECT * FROM endpoint WHERE id = ?", new Object[]{inboundEndpoint.getId()});
        assertEquals("file://temp/in", map.get("uri"));
        assertEquals(2L, map.get("endpoint_type_fk"));
    }

    @Override
    public void testLazy()
    {
        Node node = nodeDAO.findById(NODE_ID);

        assertNotNull(node);

        endTransaction();

        try
        {
            node.getRouter().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }


        try
        {
            node.getInBound().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }

        try
        {
            node.getOutBound().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }
    }
}