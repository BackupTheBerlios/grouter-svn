/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
     * @param ctx     Context
     * @param alogger if null it will usethis class logger
     */
    public static void printJNDI(Context ctx, Logger alogger)
    {
        Hashtable table;
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

    /**
     * Print out all JNDI variables for provided Context.
     *
     * @param ctx     Context
     * @param alogger if null it will usethis class logger
     */
    public static void printJNDI(Context ctx, final Log alogger)
    {
        Hashtable table;
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
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.commons.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

            Iterator<BindingItem> bindingItemIterator = bindings.iterator();
            while (bindingItemIterator.hasNext())
            {
                BindingItem item = bindingItemIterator.next();
                InitialContext initialContext = new InitialContext();
                logger.debug("Creating component context : " + item.getJndipath()[0]);
                Context compContext = initialContext.createSubcontext(item.getJndipath()[0]);
                logger.debug("Creating environment context : " + item.getJndipath()[1]);
                Context envContext = compContext.createSubcontext(item.getJndipath()[1]);
                logger.debug("Bidning : " + item.getJndiName() + " to implementation : " + item.getImplemenation());
                envContext.bind(item.getJndiName(), item.getImplemenation());
                printJNDI(envContext, logger);
                //logger.debug(envContext.listBindings(initialContext.getNameInNamespace()));
            }

        } catch (NamingException e)
        {
            logger.error(e, e);
        }

    }


    /**
     * Get context for local jboss server.
     *
     * @return the initialcontext
     * @throws NamingException  see {@link NamingException}
     */
    public static InitialContext getJbossInitialContext()
            throws NamingException
    {
        return getJbossInitialContextForServer(null);
    }

    /**
     * Get context for remote jboss server.
     *
     * @param jndiUrl the jndiurl, if null a default will be used for jboss
     * @return initialcontext
     * @throws NamingException  see {@link NamingException}
     */
    @SuppressWarnings({"UnnecessaryLocalVariable", "SameParameterValue"})
    public static InitialContext getJbossInitialContextForServer(String jndiUrl)
            throws NamingException
    {
        if (jndiUrl == null)
        {
            jndiUrl = "jnp://127.0.0.1:1099";
        }
        Hashtable<String,String> hashtable = new Hashtable<String,String>();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        hashtable.put(Context.PROVIDER_URL, jndiUrl);
        hashtable.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        InitialContext iniCtx = new InitialContext(hashtable);
        return iniCtx;
    }



    

}
