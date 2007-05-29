package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.daolayer.EndPointDAO;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LazyInitializationException;

import java.util.Map;
import java.util.List;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class EndPointDAOTest extends AbstractDAOTests
{
    EndPointDAO endPointDAO;
    private static Log log = LogFactory.getLog(NodeDAOTest.class);


    public void setEndPointDAO(EndPointDAO endPointDAO)
    {
        this.endPointDAO = endPointDAO;
    }

    void testLazyCollections()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void testFindById()
    {
        EndPoint endPoint = (EndPoint) endPointDAO.findById( ENDPOINT_ID );


      //  setComplete();
       assertNotNull(endPoint.toString());
  //      assertEquals(ENDPOINT_ID, endPoint.getId());

//        Map map = node.getInBound().getEndPointContext();
//        assertEquals( "localhost", map.get( "ftpHost" ) );
//        assertEquals( "12345", map.get( "ftpPort" ) );
    }

    public void testDelete()
    {


    }


    /**
     * Build a Node with Enpoints, save and flush. Verify that cascade mappings are correct
     * by pure sql after a flushed session.
     */
    public void testSave()
    {
    }


}