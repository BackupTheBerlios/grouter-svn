/**
 * ServiceLocatorException.java
 */
package org.grouter.common.jndi;


/**
 * Thrown when a service is not found.
 */
public class ServiceLocatorException extends RuntimeException
{
    /**
     * Constructor.
     * @param message the error message.
     */
    public ServiceLocatorException(String message)
    {
        super(message);
    }

    /**
     * Constructor.
     * @param cause the cause of this Exception.
     */
    public ServiceLocatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructor.
     * @param message the error message.
     * @param cause the cause of this Exception.
     */
    public ServiceLocatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
