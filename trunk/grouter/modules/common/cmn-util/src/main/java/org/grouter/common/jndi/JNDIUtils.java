/**
 * JNDIUtils.java
 */
package org.grouter.common.jndi;

import javax.naming.NamingException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.apache.commons.logging.*;


/**
 * Some util methods for printing out the contents of a Context to a provided logger.
 * The createInMemoryJndiProvider creates an inmemory representation of a JNDI tree
 * of object implemenations. It uses common-naming to do this.
 *
 * @author Georges Polyzois
 */
public class JNDIUtils
{
    private static Logger logger = Logger.getLogger(JNDIUtils.class);


    /**
     * Print out all JNDI variables for provided Context.
     *
     * @param ctx Context
     * @param alogger if null it will usethis class logger
     */
    public static void printJNDI(Context ctx, Logger alogger)
    {
        Hashtable table = null;
        try
        {
            table = ctx.getEnvironment();
            Set set = table.keySet();
            Iterator it = set.iterator();
            alogger.info("Context contains key value pairs:");
            while (it.hasNext())
            {
                String key = (String) it.next();
                alogger.info("(key,value) = (" + key + "," +  (table.get(key)) + ")");
            }
        } catch (NamingException ex)
        {
            logger.error("Retrieving the environment in effect for this context failed.");
        }
    }

    /**
     * Print out all JNDI variables for provided Context.
     *
     * @param ctx Context
     * @param alogger if null it will usethis class logger
     */
    public static void printJNDI(Context ctx, final Log alogger)
    {
        Hashtable table = null;
        try
        {
            table = ctx.getEnvironment();
            Set set = table.keySet();
            Iterator it = set.iterator();
            alogger.info("Context contains key value pairs:");
            while (it.hasNext())
            {
                String key = (String) it.next();
                alogger.info("(key,value) = (" + key + "," + (table.get(key)) + ")");
            }
        } catch (NamingException ex)
        {
            logger.error("Retrieving the environment in effect for this context failed.");
        }
    }


    public static void createInMemoryJndiProvider(List<BindingItem> bindings)
    {
        if (bindings == null)
        {
            throw new IllegalArgumentException("Can not bind null to jndi tree.");
        }
        try
        {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.commons.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,"org.apache.naming");

            Iterator<BindingItem> bindingItemIterator = bindings.iterator();
            while(bindingItemIterator.hasNext())
            {
                BindingItem item = bindingItemIterator.next();
                InitialContext initialContext = new InitialContext();
                logger.debug("Creating component context : " + item.getJndipath()[0]);
                Context compContext = initialContext.createSubcontext(item.getJndipath()[0]);
                logger.debug("Creating environment context : " + item.getJndipath()[1]);
                Context envContext =  compContext.createSubcontext(item.getJndipath()[1]);
                logger.debug("Bidning : " + item.getJndiName() + " to implementation : " + item.getImplemenation());
                envContext.bind(item.getJndiName(), item.getImplemenation());
                printJNDI(envContext,logger);
                //logger.debug(envContext.listBindings(initialContext.getNameInNamespace()));
            }


            /*InitialContext initialContext = new InitialContext();
            Context compContext = initialContext.createSubcontext("jndi:hibernate");
            Context envContext =  compContext.createSubcontext("mbeans");
            envContext.bind("jndi_name", "www.apache.org");
              */

        } catch (NamingException e)
        {
            logger.error(e,e);
        }

    }


}