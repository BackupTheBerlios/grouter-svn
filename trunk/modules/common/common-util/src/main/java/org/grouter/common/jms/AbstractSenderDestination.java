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

import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Georges Polyzois
 */
public abstract class AbstractSenderDestination extends AbstractDestination
{
    // Default time to live from constructor. Can be overridden in send method.
    protected long timeToLive = 0;


    /**
     * Sender can choose to wait and receive an acknowledge or reply
     * on a request from a temporary destination setup betweeen sender and listener.
     *
     * @param waitForMs time to wait for a Message from the listener
     * @return Message
     */
    @SuppressWarnings({"SameParameterValue"})
    abstract public Message waitAndGetReplyFromTemporaryDestination(long waitForMs);


    /**
     * Method for sending a string message.
     *
     * @param message String
     * @throws JMSRuntimeException an unchecked exception if failure to send the message
     */
    public abstract void sendMessage(String message) throws JMSRuntimeException;

    /**
     * This send method will override settings for deliverymode, time to live
     * and message priority. It will also take a Hashmap<String,String> and decorate
     * JMS header properties accordingly.
     *
     * <b>See documentaion in {@link org.grouter.common.jms.QueueSenderDestination#sendMessage(java.io.Serializable,int,int,long,java.util.HashMap)}.</b><br>
     * <br>
     *
     * @param message          Serializable your serializable object
     * @param deliveryMode     int see jms documentation
     * @param messagePriority  int jms documentation 0-4 are gradations of normal priority
     *                         and 5-9 are indicates expeditious handling om messages in the jms provider (mom)
     *                         service. Beware though that there is no guarantee that moms will use this informtion.
     * @param timeToLive       long see jms documentation
     * @param headerProperties HashMap a hashmap with string key value pairs used for the jms
     * @throws JMSRuntimeException an unchecked exception if failure to send the message
     */
    public abstract void sendMessage(Serializable message, int deliveryMode,
                                     int messagePriority, long timeToLive,
                                     HashMap<String, String> headerProperties)
            throws JMSRuntimeException;


    /**
     * This send method will override settings for deliverymode, time to live
     * and message priority. It will also take a Hashmap<String,String> and decorate
     * JMS header properties accordingly.
     *
     * @param message Serializable your serializable object
     * @throws JMSRuntimeException an unchecked exception if failure to send the message
     */
    public abstract void sendMessage(Message message) throws JMSRuntimeException;

    /**
     * This send method will take a Hahmap<String,String> and decorate
     * JMS header properties accordingly.
     *
     * @param message          Serializable your serializable object.
     * @param headerProperties HashMap a hashmap with string key value pairs used for the jms header.
     * @throws JMSRuntimeException an unchecked exception if failure to send the message
     */
    public abstract void sendMessage(Serializable message, HashMap<String,
            String> headerProperties) throws JMSRuntimeException;


    /**
     * Implementation will use default delivery mode, priority and time to live
     * as set by constructor.
     *
     * @param message Serializable
     * @throws JMSRuntimeException an unchecked exception if failure to send the message
     */
    public abstract void sendMessage(Serializable message) throws JMSRuntimeException;

    /**
     * Message for sending an already created javax.jms.Message
     *
     * @param message         The JMS message to send
     * @param deliveryMode    int
     * @param messagePriority int jms documentation 0-4 are gradations of normal priority
     *                        and 5-9 are indicates expeditious handling om messages in the jms provider (mom)
     *                        service. Beware though that there is no guarantee that moms will use this informtion.
     * @param timeToLive      long
     * @throws JMSRuntimeException an unchecked exception if failure to send the message
     */
    public abstract void sendMessage(Message message, int deliveryMode,
                                     int messagePriority, long timeToLive) throws JMSRuntimeException;


    /**
     * Retrieves the session for this destination. This method can be used if you
     * are handling trasacted sessions and need to explicitly get hold of the session
     * to do a commit or rollback.
     *
     * @return QueueSession
     */
    abstract public Session getSession();

}
