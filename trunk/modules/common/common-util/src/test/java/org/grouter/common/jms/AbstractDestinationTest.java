package org.grouter.common.jms;

import junit.framework.TestCase;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;
import java.util.Hashtable;

/**
 * Base class for unit tests against an in memory jms borker - ActveMQ.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractDestinationTest extends TestCase
{
    protected static final String QUEUE_TEST_QUEUE = "dynamicQueues/queue.TestrQueue";
    protected static final String QUEUE_TEST_QUEUE_TRANSACTIONAL = "dynamicQueues/queue.TestQueueTransactional";
    protected static final String TOPIC_TEST_QUEUE = "dynamicTopics/topic.TestTopic";

    public final static String JBOSSCONNECTIONFACTORY = "UIL2ConnectionFactory";
    public final static String ACTIVEMQCONNECTIONFACTORY = "ConnectionFactory";

    AbstractSenderDestination qDestination;
    AbstractListenerDestination qListener;
    AbstractSenderDestination qSenderTransactional;
    AbstractListenerDestination qListenerTransactional;
    AbstractSenderDestination qSenderTemporaryQueue;
    AbstractListenerDestination qListenerTemporaryQueue;

    
    @SuppressWarnings({"UnnecessaryLocalVariable"})
    public static InitialContext getJbossInitialContextForServer()
            throws NamingException
    {
        String jndiUrl = "jnp://localhost:1099";
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        hashtable.put(Context.PROVIDER_URL, jndiUrl);
        hashtable.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        InitialContext iniCtx = new InitialContext(hashtable);
        return iniCtx;
    }

    @SuppressWarnings({"UnnecessaryLocalVariable"})
    public static InitialContext getInMemoryActiveMqContext()
            throws NamingException
    {
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.activemq.jndi.ActiveMQInitialContextFactory");
        hashtable.put(Context.PROVIDER_URL, "vm://localhost?broker.persistent=false");
        hashtable.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        InitialContext iniCtx = new InitialContext(hashtable);
        return iniCtx;
    }
}
