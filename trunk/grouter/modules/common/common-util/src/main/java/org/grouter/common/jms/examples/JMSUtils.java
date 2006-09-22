package org.grouter.common.jms.examples;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 21, 2006
 * Time: 4:17:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class JMSUtils
{

    /**
     * Get context for local jboss server.
     *
     * @return
     * @throws NamingException
     */
    public static InitialContext getJbossInitialContext()
            throws NamingException
    {
        return getJbossInitialContextForServer(null);
    }

    /**
     * Get context for remote jboss server.
     *
     * @param jndiUrl
     * @return
     * @throws NamingException
     */
    public static InitialContext getJbossInitialContextForServer(String jndiUrl)
            throws NamingException
    {

        if (jndiUrl == null)
        {
            jndiUrl = "jnp://localhost:1099";
        }
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        hashtable.put(Context.PROVIDER_URL, jndiUrl);
        hashtable.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        InitialContext iniCtx = new InitialContext(hashtable);
        return iniCtx;
    }


}
