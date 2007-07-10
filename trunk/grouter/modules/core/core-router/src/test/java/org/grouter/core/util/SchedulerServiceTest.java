package org.grouter.core.util;

import org.grouter.domain.entities.Node;
import org.grouter.core.readers.AbstractFileReaderTests;


import java.util.Set;
import java.util.HashSet;

/**
 * @author Georges Polyzois
 */
public class SchedulerServiceTest extends AbstractFileReaderTests
{
    public void testSchedulerService()
    {
        try
        {
            SchedulerService schedulerFactory = new  SchedulerService( null );
            fail();
        } catch (Exception e)
        {
            // expected
        }

        try
        {
            Set<Node>  nodes = new HashSet<Node>();
            nodes.add( null ) ;
            SchedulerService schedulerFactory = new  SchedulerService( null );
            schedulerFactory.setNodes(nodes);
            fail();
        } catch (Exception e)
        {
            // expected
        }
    }


    public void testStartUp()
    {
        SchedulerService schedulerService = new  SchedulerService( router.getNodes() );
        try
        {
            schedulerService.startAllNodes();
            schedulerService.rescheduleNode( fileToFileNode  );
            schedulerService.rescheduleNode( fileToFileNode  );

            Thread.sleep(20000);
        } catch (Exception e)
        {
            fail();
        }

        
    }
}
