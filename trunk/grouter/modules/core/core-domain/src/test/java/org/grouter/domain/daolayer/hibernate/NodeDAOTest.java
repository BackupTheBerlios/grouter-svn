package org.grouter.domain.daolayer.hibernate;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.grouter.domain.entities.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Test the DAO using an external DB which is populated with data before the actual test is run
 * and then restored to a state as per before the tests were ran. All plumibng for rolling back the
 * state is taken care of by Springs  {@link org.springframework.test.AbstractTransactionalDataSourceSpringContextTests}
 * we we are subclassing from in AbstractDAOTests. AbstractDAOTests referes to the sql to be run before
 * any test are created and run.
 *
 * The only problem with these types of test are that as we go along and add more and more test the tests
 * start taking considerably long time to execute.
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
        Node resultNotFound = nodeDAO.findById("nodeid_1");
        //assertTrue(resultNotFound.toString());
    }


    public void testStore()
    {

        Node node = new Node();

        node.setName("A node");


        nodeDAO.save(node);
        flushSession();

        String id = node.getId();

        // motor_dealer table
        Map motorDealerMap = jdbcTemplate.queryForMap("SELECT * FROM node WHERE id = ?",
                new Object[]{id});
        assertEquals("A node", motorDealerMap.get("name"));


    }
}
