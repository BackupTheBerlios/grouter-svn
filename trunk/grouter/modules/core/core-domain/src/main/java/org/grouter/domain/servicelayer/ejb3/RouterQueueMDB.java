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

package org.grouter.domain.servicelayer.ejb3;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Node;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * A Message Driven Bean registering for callbacks on a in queue, receiving events.
 */

@SuppressWarnings({"EjbErrors"})
@MessageDriven(activationConfig =
{
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/GrouterQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto.acknowledge")
})
public class RouterQueueMDB implements MessageListener
{
    //@JndiInject(jndiName = ExternalSystemsLocal.LOOKUP_NAME)
    //private ExternalSystemsHandler externalSystemsHandler;

    private static Logger logger = Logger.getLogger(RouterQueueMDB.class);

    

    // This does not work in jboss...
    //@EJB
    //private GRouterLocalService gRouterLocal;

    @SuppressWarnings({"EjbErrorInspection"})
    @Resource
    private SessionContext sc;


    /**
     * Method called when new message arrives on destination. Callback from JMS container.
     *
     * @param receivedMessage
     */
    public void onMessage(Message receivedMessage)
    {
        try
        {
            ObjectMessage objectMessage = (ObjectMessage) receivedMessage;
            logger.debug("Got message : " + objectMessage.getObject());
            if (objectMessage.getObject() instanceof org.grouter.domain.entities.Message)
            {
                persist(objectMessage);

            }
            if (objectMessage.getObject() instanceof org.grouter.domain.entities.Node)
            {
                persistNode(objectMessage);

            } else
            {
                logger.debug("Got unknown type of message!!!");
            }

        } catch (Exception e)
        {
            logger.error(e, e);
        }
    }

    /**
     * Call session ejb for processing of event.
     * 
     * @param objectMessage
     * @throws JMSException
     */
    private void persist(ObjectMessage objectMessage)
            throws JMSException
    {
        org.grouter.domain.entities.Message message = (org.grouter.domain.entities.Message) objectMessage.getObject();
        logger.debug("Got message");
        RouterLocalService gRouterLocal = (RouterLocalService)sc.lookup( RouterLocalService.DOMAIN_GROUTER_BEAN_LOCAL );
        gRouterLocal.saveMessage(message);
    }

    /**
     * Call session ejb for processing of event.
     *
     * @param node
     * @throws JMSException
     */
    private void persistNode(ObjectMessage node)
            throws JMSException
    {
        Node message = (Node) node.getObject();
        logger.debug("Got node status update");
        RouterLocalService gRouterLocal = (RouterLocalService)sc.lookup( RouterLocalService.DOMAIN_GROUTER_BEAN_LOCAL );
        gRouterLocal.saveNode(message);
    }


    /**
     * Util method.
     * 
     * @param messages
     * @return
     */


    /*
    private String printMessages(org.grouter.domain.entities.Message[] messages)
    {
        StringBuffer stringBuffer = new StringBuffer();
        for (org.grouter.domain.entities.Message message : messages)
        {
            stringBuffer.append(message.reflectionToString()).append("\n");
        }
        return stringBuffer.toString();

    } */
}
