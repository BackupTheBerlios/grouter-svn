package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.EndPointDAO;
import org.grouter.domain.entities.EndPoint;

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

    @Override
    public void testLazyCollections()
    {
        assertTrue(true);
    }

    @Override
    public void testFindById()
    {
        EndPoint endPoint = (EndPoint) endPointDAO.findById(ENDPOINT_ID);

        //  setComplete();
        assertNotNull(endPoint.toString());
        //      assertEquals(ENDPOINT_ID, endPoint.getId());

//        Map map = node.getInBound().getEndPointContext();
//        assertEquals( "localhost", map.get( "ftpHost" ) );
//        assertEquals( "12345", map.get( "ftpPort" ) );
    }

    @Override
    public void testDelete()
    {
        assertTrue(true);
    }


    @Override
    public void testSave()
    {
        assertTrue(true);
    }


}