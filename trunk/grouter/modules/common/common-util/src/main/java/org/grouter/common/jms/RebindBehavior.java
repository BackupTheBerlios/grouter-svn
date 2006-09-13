/**
 * RebindBehavior.java
 */
package org.grouter.common.jms;


/**
 * Interface for different rebinding strategies.
 *
 * @author Georges Polyzois
 * @version 
 */
public abstract class RebindBehavior
{
    /**
     * Algorithm for rebinding to JMS provides is implemented by
     * classes implementeint this interface.
     */
    abstract void rebind(Destination dest);
}
