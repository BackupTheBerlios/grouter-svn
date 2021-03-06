package org.grouter.core.util;

import junit.framework.TestCase;
import org.grouter.domain.entities.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Georges
 */
public class TestSchedulerService extends TestCase
{
    SchedulerService schedulerService;

    public void setUp()
    {
    }

    public void testConstructor()
    {
        try
        {
            schedulerService = new SchedulerService( null );
            fail();
        } catch (Exception e)
        {
            // expected
        }
    }

    public void testStart()
    {

        Node node = new Node("-test","name");
        Set<Node> setNodes = new HashSet<Node>();
        setNodes.add( node );
        
        schedulerService = new SchedulerService( setNodes  );
        schedulerService.setNodes(setNodes);

        try
        {
            schedulerService.startAllNodes();
        } catch (Exception e)
        {
            fail();
        }
        finally
        {
            try
            {
                schedulerService.shutdown();
            } catch (Exception e)
            {
                //ignore
            }
        }

    }

}
