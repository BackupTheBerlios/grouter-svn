package org.grouter.core;

/**
 * @author Georges Polyzois
 */
public class GRouterServerTest extends AbstractGrouterTests
{
    public void testStartGrouterWithValidConfig()
    {
        GRouterServer grouter = new GRouterServer( router );
        grouter.startGrouter();
    }

    void doSetup()
    {

    }
}
