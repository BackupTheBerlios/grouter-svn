package org.grouter.core;

/**
 * @author Georges Polyzois
 */
public class GRouterServerTest extends AbstractGrouterTests
{
    public void testStartGrouterWithValidConfig() throws Exception
    {
        GRouterServer grouter = new GRouterServer( router );
        grouter.startGrouter( );

        Thread.sleep(200000);
    }

    public void doSetup()
    {

    }
}
