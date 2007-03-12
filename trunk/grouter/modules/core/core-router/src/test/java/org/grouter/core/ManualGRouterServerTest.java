package org.grouter.core;

import junit.framework.TestCase;

/**
 * @author Georges Polyzois
 */
public class ManualGRouterServerTest extends TestCase
{
    public void manualTestStartGrouterWithValidConfig()
    {
        GRouterServer grouter = new GRouterServer( "/Users/geopol/projects/svn/grouter/trunk/grouter/modules/core/core-router/src/test/resources/grouterconfig_file_file.xml" );
        grouter.startGrouter();
    }
}
