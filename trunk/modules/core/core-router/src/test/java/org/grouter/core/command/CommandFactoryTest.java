package org.grouter.core.command;

import junit.framework.TestCase;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;

/**
 * Unit testing the CommandFactoty.
 *
 * @author Georges Polyzois
 */
public class CommandFactoryTest extends TestCase
{
    public void testNullNode()
    {
        try
        {
            CommandFactory.getCommand(null);
            fail("A null node should raise an exception");
        } catch (Exception e)
        {
            // expected
        }
    }

    public void testValidNode()
    {
        Node node = new Node();
        node.setIdNo("anid");

        EndPoint inboundEndpoint = new EndPoint();
        inboundEndpoint.setId("id1");

        EndPoint outBoundPoint = new EndPoint();
        outBoundPoint.setId("id");
        outBoundPoint.setEndPointType(EndPointType.FILE_WRITER);

        node.setInBound(inboundEndpoint);
        node.setOutBound(outBoundPoint);


        assertNotNull(CommandFactory.getCommand(node));
        assertTrue(CommandFactory.getCommand(node) instanceof FileWriteCommand);

        outBoundPoint.setEndPointType(EndPointType.JMS_WRITER);
        assertNotNull(CommandFactory.getCommand(node));
        assertTrue(CommandFactory.getCommand(node) instanceof JmsWriteCommand);

        outBoundPoint.setEndPointType(EndPointType.FTP_WRITER);
        assertNotNull(CommandFactory.getCommand(node));
        assertTrue(CommandFactory.getCommand(node) instanceof FtpWriteCommand);

        outBoundPoint.setEndPointType( null );
        try
        {
            CommandFactory.getCommand(node);
            fail( "A null endpoint type should be guearded and raise an exception" );
        } catch (Exception e)
        {
            // expected
        }

    }


}
