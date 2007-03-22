package org.grouter.domain.servicelayer.spring.logging;

import org.grouter.domain.entities.Message;

/**
 * @author Georges Polyzois
 */
public interface LogStrategy
{
    void log( Message message );
}
