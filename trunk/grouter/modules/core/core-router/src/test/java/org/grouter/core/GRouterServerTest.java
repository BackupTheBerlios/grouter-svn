package org.grouter.core;

import org.grouter.core.readers.AbstractFileReaderTests;

/**
 * @author Georges Polyzois
 */
public class GRouterServerTest extends AbstractFileReaderTests
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
