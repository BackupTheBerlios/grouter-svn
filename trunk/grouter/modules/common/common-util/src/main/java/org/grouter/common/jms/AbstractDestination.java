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

package org.grouter.common.jms;

import java.util.*;

import javax.jms.ExceptionListener;
import javax.jms.Session;
import javax.naming.*;

import org.apache.log4j.*;
import org.grouter.common.jndi.ServiceLocatorContextAware;
import org.grouter.common.exception.RemoteGrouterException;


/**
 * A Destination is defined in JMS to be either a Topic or Queue.
 * <p/>
 * E.g.
 * <pre>
 * InitialContext iniCtx = JMSUtils.getJbossInitialContext();
 * Destination queueDestination = new QueueDestination(QUEUE_TEST_QUEUE, true, "ConnectionFactory",   null, iniCtx, 4000, null, AcknowledgeMode.NONE);
 * queueDestination.bind();
 * queueDestination.sendMessage("A message");
 * logger.info("Message sent");
 * </pre>
 * <p/>
 * <p/>
 * Purpose of Destination is to hide some JMS plumbing code using this Fascade and adding a
 * rebindbehavious based on a strategy pattern.
 * You can set a rebind behavior dynamically or statically in constructors. Be sure to use the
 * correct one depending on your needs. Following descission tree might help:
 * Is messaging transactional?
 * -> yes   be sure to use commit when sending messaes using a Destination
 * <p/>
 * -> no -> acknowledge mode auto (messages are automatically acked
 * on a session and delivered only once)
 * -> duplicates ok (messages are automatically acked
 * on a session like in acknowledge mode auto and delivered
 * <b>at least</b> once)
 * -> client mode (messages are not automatically acknowledged
 * and need to be acknowledged in in code -> more complex code)
 *
 * @author Georges Polyzois
 */
public abstract class AbstractDestination
{
    //Logger.
    private static Logger logger = Logger.getLogger(AbstractDestination.class);
    //Is sender?
//    protected boolean isSender;
    // Context to use when looking up things.
    protected Context context;
    // Service locator for caching and looking up jndi resources.
    protected ServiceLocatorContextAware serviceLocatorContextAware;
    //Transactional.
    protected boolean isTransactional;
    //Ack mode.
    protected int acknowledgeMode;
    // Name of Queue or Topic.
    protected String destinationName;
    //Exceptionlistener.
    protected ExceptionListener exceptionListener;
    //Connection factory JNDI name.
    protected String connectionFactory;
    public final static String ACTIVEMQCONNECTIONFACTORY = "ConnectionFactory";
    public final static String JBOSSCONNECTIONFACTORY = "UIL2ConnectionFactory";

    // Default message priority if none is given
    protected int messagePriority = 0;
    // Used for response on requests for synchronous messaging in a session.
    protected boolean useTemporaryReplyDestination = false;
    /**
     * A strategy to use for rebinding. Setting default rebind behavior -
     * can be changed dynamically using setter or through constructor
     *
     * @label has a behavior
     * @directed
     */
    protected RebindBehavior rebindBehavior = new EternalRebind();

   
    /**
     * Disconnect from queue. This method should be called from the ejbRemove method
     * if you are using a stateless session bean.
     *
     * @throws RemoteGrouterException Unchecked exception.
     */
    public abstract void unbind() throws RemoteGrouterException;

    /**
     * Connect to destination and open session.
     *
     * @throws RemoteGrouterException Unchecked exception.
     */
    public abstract void bind() throws RemoteGrouterException;

    /**
     * Handle over rebinding to behavior interface implementation (strategy pattern).
     * <b>See doocumentaion in {@link org.grouter.common.jms.QueueSenderDestination#sendMessage(java.io.Serializable,java.util.HashMap\<String, String>)}.</b><br>
     *
     * @param dest Destination
     * @throws RemoteGrouterException Unchecked exception.
     */
    abstract public void rebind(AbstractDestination dest) throws RemoteGrouterException;



    /**
     * Get destination name.
     *
     * @return java.lang.String
     */
    public String getDestinationName()
    {
        return destinationName;
    }

    /**
     * Sets rebind behaviour dynamically.
     *
     * @param rebindBehavior RebindBehavior
     */
    public void setRebindBehavior(RebindBehavior rebindBehavior)
    {
        this.rebindBehavior = rebindBehavior;
    }



    /**
     * Create a new messageconsumer and add the listener {#link javax.jms.MessageListener}
     * to the consumer.
     * Can be used in a use cases where we want to consume a number of
     * events and remove our message listener, the process the events and reconnect our
     * messagelistener.
     */
//    abstract public void setMessageListenerOnConsumer();

    /**
     * Get Session acknowledge mode from AcknowledgeMode.
     *
     * @param ackmode return an int for corresponding AcknowledgeMode
     *
     * @return int represents Session acknowledge modes
     */
    public int getAcknowledgeMode(AcknowledgeMode ackmode)
    {
        switch (ackmode)
        {
            case AUTO_ACKNOWLEDGE:
                return Session.AUTO_ACKNOWLEDGE;
            case DUPS_OK_ACKNOWLEDGE:
                return Session.DUPS_OK_ACKNOWLEDGE;
            case CLIENT_ACKNOWLEDGE:
                return Session.CLIENT_ACKNOWLEDGE;
            case NONE:
                return -1; //indicates transactional mode is used
            default:
                return Session.AUTO_ACKNOWLEDGE;
        }
    }


    public void printJNDI(Context ctx, Logger alogger)
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

}
