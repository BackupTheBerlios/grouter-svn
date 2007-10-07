package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.entities.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

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

    public void testFindById()
    {
        Node node = nodeDAO.findById(NODE_ID_FTP);
        assertNotNull(node.toString());
        assertEquals(NODE_ID_FTP, node.getId());
        assertEquals( NodeStatus.NOTSTARTED.getId() , node.getNodeStatus().getId() );

        Map map = node.getInBound().getEndPointContext();
        assertEquals( "localhost", map.get( "ftpHost" ) );
        assertEquals( "12345", map.get( "ftpPort" ) );
    }

    public void testDelete()
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM router WHERE id = '" + ROUTER_ID + "'"));
//        assertEquals(2, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE router_fk = '" + ROUTER_ID + "'"));
        nodeDAO.delete(NODE_ID);
        flushSession();
//        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM router WHERE id = '" + ROUTER_ID + "'"));
 //       assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE router_fk = '" + ROUTER_ID + "'"));
        // one node left with two enpoints
//        assertEquals(2, jdbcTemplate.queryForInt("SELECT count(*) FROM endpoint"));

//        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM router WHERE id = '" + ROUTER_ID + "'"));
//        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE router_fk = '" + ROUTER_ID + "'"));
        nodeDAO.delete(NODE_ID_FTP);
        flushSession();
//        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM router WHERE id = '" + ROUTER_ID + "'"));
//        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM node WHERE router_fk = '" + ROUTER_ID + "'"));
        // no node left and no enpoints
//        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM endpoint"));

    }


    /**
     * Build a Node with Enpoints, save and flush. Verify that cascade mappings are correct
     * by pure sql after a flushed session.
     */
    public void testSave()
    {
        Node node = new Node();
        node.setId("andid");
        node.setDisplayName("A node");
        node.setNodeStatus( NodeStatus.NOTSTARTED );

        nodeDAO.save(node);
        flushSession();

        assertNotNull( node.getNodeStatus().getId() );

        String id = node.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM node WHERE id = ?", new Object[]{id});
        assertEquals("A node", map.get("displayname"));
    }

    public void testStoreWithEndpoints()
    {
        Node node = new Node();
        node.setDisplayName("A node");
        node.setId("anid");

        EndPoint inboundEndpoint = new EndPoint();
        inboundEndpoint.setId("id1");
        inboundEndpoint.setScheduleCron("* * * * * ");
        inboundEndpoint.setUri("file://temp/in");
        inboundEndpoint.setEndPointType(EndPointType.FILE_READER);

        EndPointContext endPointContext = new EndPointContext( "key1" , "value1", inboundEndpoint );
        EndPointContext endPointContext2 = new EndPointContext( "key2" , "value2", inboundEndpoint );
        Map contextMap = new HashMap();
        contextMap.put( endPointContext.getKeyName(), endPointContext.getValue() );
        contextMap.put( endPointContext2.getKeyName(), endPointContext2.getValue() );
        inboundEndpoint.setEndPointContext( contextMap );

        EndPoint outBoundPoint = new EndPoint();
        outBoundPoint.setId("id");
        outBoundPoint.setScheduleCron("* * * * * ");
        outBoundPoint.setUri("file://temp/out");
        outBoundPoint.setEndPointType(EndPointType.FILE_WRITER);

        node.setInBound(inboundEndpoint);
        node.setOutBound(outBoundPoint);

        nodeDAO.save(node);
        flushSession();


        String id = node.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM node WHERE id = ?", new Object[]{id});
        assertEquals("A node", map.get("displayname"));

        map = jdbcTemplate.queryForMap("SELECT * FROM endpoint_context WHERE endpoint_fk = ? AND keyname = ?", new Object[]{inboundEndpoint.getId(), "key1"});
        assertEquals("value1", map.get("value"));

    }

    @Override
    public void testLazyCollections()
    {
        Node node = nodeDAO.findById(NODE_ID);

        assertNotNull(node);

        // end transaction to simulate a remote request where the session was closed
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


        try
        {
            node.getOutBound().getEndPointContext().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }
    }


    public void testGetNumberOfMessages()
    {
        assertEquals(new Long(6), nodeDAO.getNumberOfMessages(NODE_ID));
    }


    public void testGetNumberOfMessages2()
    {
        List<Node> nodes = nodeDAO.findNodesWithNumberOfMessages(ROUTER_ID);

        assertNotNull(nodes);

        assertEquals(2, nodes.size());

        for (Node node : nodes)
        {
            if (node.getId().equalsIgnoreCase(NODE_ID))
            {
                assertEquals(new Long(6), node.getNumberOfMessagesHandled());
            }
        }


    }

}
