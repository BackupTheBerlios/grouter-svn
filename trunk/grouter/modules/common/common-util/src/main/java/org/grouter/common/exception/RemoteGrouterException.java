package org.grouter.common.exception;

import java.rmi.RemoteException;

/**
 * Unchecked wrapper exception of {@link java.rmi.RemoteException}.
 *
 * @author Georges Polyzois
 */
public class RemoteGrouterException extends GrouterRuntimeException
{

    /**
     * Create new exception.
     *
     * @param ex is the original remote exception.
     */
    public RemoteGrouterException(String message, Exception ex)
    {
        super(message, ex);
    }
}
