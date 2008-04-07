package org.grouter.core.command;

import junit.framework.TestCase;
import org.grouter.domain.entities.EndPoint;
import org.grouter.domain.entities.EndPointType;
import org.grouter.domain.entities.Node;

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
        node.setIdNo("anid");

        EndPoint inboundEndpoint = new EndPoint();

        EndPoint outBoundPoint = new EndPoint();
        outBoundPoint.setEndPointType(EndPointType.FILE_WRITER);

        node.setInBound(inboundEndpoint);
        node.setOutBound(outBoundPoint);

        FileWriteCommand fileWriteCommand = new FileWriteCommand(node);

        fileWriteCommand.write();

    }
}
