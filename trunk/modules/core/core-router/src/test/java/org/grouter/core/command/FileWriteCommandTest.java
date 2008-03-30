package org.grouter.core.command;

import junit.framework.TestCase;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;

/**
 *
 * @author Georges Polyzois
 */
public class FileWriteCommandTest extends TestCase
{
    public void testValidation()
    {
        try
        {
            new FileWriteCommand(null);
            fail( "Mus be a non null Node" );
        } catch (Exception e)
        {
            //expected
        }
    }

    public void testWrite()
    {
        Node node = new Node();
        node.setId("anid");

        EndPoint inboundEndpoint = new EndPoint();
        inboundEndpoint.setId("id1");

        EndPoint outBoundPoint = new EndPoint();
        outBoundPoint.setId("id");
        outBoundPoint.setEndPointType(EndPointType.FILE_WRITER);

        node.setInBound(inboundEndpoint);
        node.setOutBound(outBoundPoint);

        FileWriteCommand fileWriteCommand = new FileWriteCommand(node);

        fileWriteCommand.write();

    }
}
