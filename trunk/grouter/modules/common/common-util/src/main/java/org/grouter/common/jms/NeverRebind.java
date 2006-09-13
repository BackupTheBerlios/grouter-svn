/**
 * NeverRebind.java
 */
package org.grouter.common.jms;

import org.apache.log4j.Logger;

/**
 * This "algorithm" just unbindes and leaves it with that.
 *
 * @author
 * @version
 */
public class NeverRebind extends RebindBehavior
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(NeverRebind.class);

    /**
     * Default constructor.
     */
    public NeverRebind()
    {
    }

    /**
     * The algorithm implementation for rebinding goes here.
     */
    public void rebind(Destination dest)
    {
        logger.info("Rebinding using behavior implementation : " + this.getClass().getName());
        // We unbind and do nothing more
        dest.unbind();
        logger.debug("We unbinded from the destination, if any.");
    }
}
