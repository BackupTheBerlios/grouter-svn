package org.grouter.core;

/**
 * @author Georges Polyzois
 */
public class GRouterServerManual extends AbstractGrouterTests
{
    public void testStartGrouterWithValidConfig()
    {
        GRouterServer grouter = new GRouterServer( router );
        grouter.startGrouter( );
    }

    public void doSetup()
    {

    }
}
