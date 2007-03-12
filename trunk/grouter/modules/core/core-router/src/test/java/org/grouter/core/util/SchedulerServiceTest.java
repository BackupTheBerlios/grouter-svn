package org.grouter.core.util;

import org.grouter.domain.entities.Node;
import org.grouter.core.AbstractGrouterTests;


import java.util.Set;
import java.util.HashSet;

/**
 * @author Georges Polyzois
 */
public class SchedulerServiceTest extends AbstractGrouterTests
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
            SchedulerService schedulerFactory = new  SchedulerService( nodes );
            fail();
        } catch (Exception e)
        {
            // expected
        }
    }


    public void testStartUp() throws Exception
    {
        setComplete();

        SchedulerService schedulerFactory = new  SchedulerService( router.getNodes() );
        schedulerFactory.start();

        Thread.sleep( 10000 );


        

    }
}
