/**
 * XTimesRebind.java
 */
package org.grouter.common.jms;

import org.apache.log4j.Logger;

/**
 * Rebinds x number of times.
 *
 * @author
 * @version
 */
public class XTimesRebind extends RebindBehavior
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(XTimesRebind.class);
    /** Counter for max number of retires to rebind. */
    private int numberOfTimes = 1;

    /**
     * Constructor.
     *
     * @param numberOfTimes int
     */
    public XTimesRebind(int numberOfTimes)
    {
        this.numberOfTimes = numberOfTimes;
    }

    /**
     * The algorithm implementation for rebinding goes here.
     */
    public void rebind(Destination dest)
    {
        logger.info("Rebinding using behavior implementation : " + this.getClass().getName());
        for (int i = 0; i < numberOfTimes; i++)
        {
            dest.unbind();
            dest.bind();
            logger.debug("We rebinded to the destination on attempt number " + numberOfTimes);
            return;
        }
    }
}
