/**
 * SystemJMSExceptionListenerHandler.java
 */
package org.grouter.common.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.Logger;

/**
 * ExceptionListener called from JMS provider when something goes wrong
 * like 'ping timeout'.
 *
 * @author
 * @version
 */
public class SystemJMSExceptionListenerHandler implements ExceptionListener
{
    /** Logger. */
    private static Logger logger = Logger.getLogger(SystemJMSExceptionListenerHandler.class);

    /** Strategy to use for rebinding. We set it to eternal and wait for
     * JMS provider to restart.
     */
    private RebindBehavior rebindBehavior = new EternalRebind();
    /** Destination on which a rebind will issued on if needed. */
    private Destination destination;

    /**
     * Constructor taking a {@link SystemJMSExceptionListenerHandler}.
     *
     * @param dest Destination  to perform rebind on.
     */
    public SystemJMSExceptionListenerHandler(Destination dest)
    {
        this.destination = dest;
    }

    /**
     * Logging the exception and trying a rebind to JMS provider using
     * the {@link SystemJMSExceptionListenerHandler}.
     *
     * @param exception
     * @see javax.jms.ExceptionListener#onException(javax.jms.JMSException)
     */
    public void onException(JMSException exception)
    {
        logger.info("Trying to rebind accourding to strategy!!");
        rebindBehavior.rebind(destination);
    }
}
