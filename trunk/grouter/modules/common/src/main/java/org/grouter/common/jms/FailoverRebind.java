/**
 * FailoverRebind.java
 */
package org.grouter.common.jms;

import org.apache.log4j.Logger;


/**
 * Intended to rebind to another context - needs implementation.
 *
 * @author
 * @version
 */
public class FailoverRebind extends RebindBehavior
{
    public FailoverRebind()
    {
    }

    /** Logger. */
    private static Logger logger = Logger.getLogger(XTimesRebind.class);

    void rebind(Destination dest)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
