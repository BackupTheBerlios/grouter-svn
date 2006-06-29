package org.grouter.common.exception;

import java.rmi.RemoteException;

/**
 * Unchecked wrapper exception of {@link java.rmi.RemoteException}.
 */
public class RemoteSiriException extends SiriRuntimeExceptionException
{
    private static final String REMOTE_EXCEPTION_MESSAGE = "RemoteException occured.";

    /**
     * Create new exception with the default severity level {@link SiriRuntimeExceptionException#ERROR}.
     *
     * @param ex is the original remote exception.
     */
    public RemoteSiriException(RemoteException ex)
    {
        super(REMOTE_EXCEPTION_MESSAGE, ex, SiriRuntimeExceptionException.ERROR);
    }
}
