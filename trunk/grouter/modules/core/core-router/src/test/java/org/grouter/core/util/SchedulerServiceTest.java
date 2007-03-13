package org.grouter.core.util;

import org.grouter.domain.entities.Node;
import org.grouter.core.AbstractGrouterTests;
import org.apache.commons.io.FileUtils;


import java.util.Set;
import java.util.HashSet;
import java.io.File;

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

        Thread.sleep( 60000 );


        

    }
}
