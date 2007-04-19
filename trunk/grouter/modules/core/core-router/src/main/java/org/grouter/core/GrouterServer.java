package org.grouter.core;

import java.rmi.RemoteException;

/**
 * This interface is exposed using RMI so that remote clients can invoke operations on the Grouter.
 *
 * @author Georges Polyzois
 */
public interface GrouterServer
{
    void stopNode( String nodeId ) throws Exception;
}
