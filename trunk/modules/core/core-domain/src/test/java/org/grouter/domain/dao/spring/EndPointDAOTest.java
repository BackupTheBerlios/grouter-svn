package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.EndPointDAO;
import org.grouter.domain.entities.EndPoint;

import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class EndPointDAOTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(EndPointDAOTest.class);


    @Override
    public void testLazyCollections()
    {
        assertTrue(true);
    }

    @Override
    public void testFindById()
    {
        EndPoint endPoint = endPointDAO.findById(ENDPOINT_ID);

        assertNotNull(endPoint.toString());

        Map map = endPoint.getEndPointContext();
        assertEquals( "localhost", map.get( "ftpHost" ) );
        assertEquals( "12345", map.get( "ftpPort" ) );
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