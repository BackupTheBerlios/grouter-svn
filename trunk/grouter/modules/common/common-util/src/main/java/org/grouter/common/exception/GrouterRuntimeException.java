package org.grouter.common.exception;

/**
 * General unchecked exception used for communcation of all technical
 * problems found in the source code.
 */
public class GrouterRuntimeException extends RuntimeException
{
    /**
     * New exception.
     *
     * @param message String is a text description of the reason for the error.
     */
    public GrouterRuntimeException(String message)
    {
        super(message);
    }


    /**
     * New exception - try using th
     *
     * @param message   String is a text description of the reason for the error.
     * @param rootcause is the root cause for this exception.
     */
    public GrouterRuntimeException(String message, Throwable rootcause)
    {
        super(message, rootcause);
    }
}
