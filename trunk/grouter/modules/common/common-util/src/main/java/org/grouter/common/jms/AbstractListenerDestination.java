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

import org.apache.log4j.Logger;

import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Listener destination.
 * 
 * @author Georges Polyzois
 */
public abstract class AbstractListenerDestination extends AbstractDestination
{
    private static Logger logger = Logger.getLogger(AbstractListenerDestination.class);
    //Receiver for the Queue.
    protected MessageConsumer messageConsumer;
    // Registered MessgeListener.
    protected MessageListener listener;


    /**
     * Method to retrieve the consumer for this session. A consumer can not
     * exist if state of this object was created with isSender true - see
     * constructor. An <b>IllegalStateException</b> is raised if this is done.
     *
     * A MessageConsumer is useful if we want to call receive on the session ourselves.
     *
     * @return MessageConsumer
     */
    public MessageConsumer getMessageConsumer()
    {
        return messageConsumer;
    }


    /**
     * Remove the Messagelistener on this messageconsumer. This will make your
     * messagelistener stop receiving messages from the session.
     */
    final public synchronized void closeConsumer()
    {
        if (messageConsumer != null)
        {
            try
            {
                logger.debug("Closing consumer.");
                messageConsumer.close();
                messageConsumer = null;
            } catch (JMSException ex)
            {
                logger.error(
                        "Failed closing messageconsumer.",
                        ex);
            }
        }
    }
    
    /**
     * Send the reply on a temporary destination and set the correlation
     * id on the JMS header before sending it. This is an ACK sent from the listener.
     *
     * @param request Message
     */
    abstract public void sendReplyToTemporaryDestination(Message request);
    


    
}
