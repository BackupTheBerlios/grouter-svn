/**
 * EternalRebind.java
 */
package org.grouter.common.jms;

import org.apache.log4j.Logger;
import java.util.concurrent.TimeUnit;

/**
 * EternalRebind will continue to rebind 4ever. It will gracefully
 * wait longer end longer before rebinding again up till a maximumWaitForREbind
 * time period.
 *
 * @author Georges Polyzois
 * @version
 */
public class EternalRebind extends RebindBehavior
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(EternalRebind.class);
    /** Rebind counter. */
    private int rebindCounter = 0;
    private int maximumWaitForREbind;

    /**
     * Constructor.
     */
    public EternalRebind()
    {
    }

    /**
     * The algorithm implementation for rebinding goes here.
     */
    public synchronized void rebind(Destination dest)
    {
        logger.info("Rebinding using behavior implementation : " + this.getClass().getName());
        boolean isBoundToDestination = false;
        while(!isBoundToDestination)
        {
            hold();
            dest.unbind();
            dest.bind();
            isBoundToDestination = true;
            rebindCounter = 0;
        }
    }

    /**
     * Hold increasingly up until maximumWaitForREbind seconds.
     */
    private void hold()
    {
        long holdTime = 1 * rebindCounter;
        try
        {
            TimeUnit.SECONDS.sleep(holdTime);
        } catch (InterruptedException ex)
        {
            //do nothing
        }
        maximumWaitForREbind = 10;
        if(rebindCounter <maximumWaitForREbind)
        {
            rebindCounter++;
        }
    }
}
