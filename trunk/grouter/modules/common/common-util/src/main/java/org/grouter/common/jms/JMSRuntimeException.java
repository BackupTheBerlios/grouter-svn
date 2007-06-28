package org.grouter.common.jms;

/**
 * General unchecked exception used for communcation of all technical
 * problems found in the source code.
 *
 * @author Georges Polyzois
 */
public class JMSRuntimeException extends RuntimeException
{
    /**
     * New exception.
     *
     * @param message String is a text description of the reason for the error.
     */
    public JMSRuntimeException(String message)
    {
        super(message);
    }


    /**
     * New exception - try using th
     *
     * @param message   String is a text description of the reason for the error.
     * @param rootcause is the root cause for this exception.
     */
    public JMSRuntimeException(String message, Throwable rootcause)
    {
        super(message, rootcause);
    }
}
