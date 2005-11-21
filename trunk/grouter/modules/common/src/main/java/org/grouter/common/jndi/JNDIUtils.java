/**
 * JNDIUtils.java
 */
package org.grouter.common.jndi;

import javax.naming.NamingException;
import java.util.Iterator;
import java.util.Set;
import java.util.Hashtable;
import javax.naming.Context;
import org.apache.log4j.Logger;

/**
 * Some util methods.
 *
 */
public class JNDIUtils
{
    /**
     * Print out all JNDI variables for provided Context.
     *
     * @param ctx Context
     */
    public static void printJNDI(Context ctx, Logger logger)
    {
        Hashtable table = null;
        try
        {
            table = ctx.getEnvironment();
            Set set = table.keySet();
            Iterator it = set.iterator();
            logger.info("Context contains key value pairs:");
            while (it.hasNext())
            {
                String key = (String) it.next();
                logger.info("(key,value) = (" + key + "," +
                                   (table.get(key)) + ")");
            }
        } catch (NamingException ex)
        {
            logger.error("Retrieving the environment in effect for this context failed.");
        }
    }
}
