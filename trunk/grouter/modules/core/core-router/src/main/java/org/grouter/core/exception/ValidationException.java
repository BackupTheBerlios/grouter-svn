package org.grouter.core.exception;

/**
 * @author Georges
 */
public class ValidationException extends RuntimeException
{
    /**
     * Constructor.
     * @param message the error message.
     */
    public ValidationException(String message)
    {
        super(message);
    }

    /**
     * Constructor.
     * @param cause the cause of this Exception.
     */
    public ValidationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructor.
     * @param message the error message.
     * @param cause the cause of this Exception.
     */
    public ValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
