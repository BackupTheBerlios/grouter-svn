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

/**
 * General unchecked exception used for communcation of all technical
 * problems found in the source code.
 *
 * @author Georges Polyzois
 */
public class JMSRuntimeException extends RuntimeException
{
    /**
     * New exception.
     *
     * @param message String is a text description of the reason for the error.
     */
    public JMSRuntimeException(String message)
    {
        super(message);
    }


    /**
     * New exception - try using th
     *
     * @param message   String is a text description of the reason for the error.
     * @param rootcause is the root cause for this exception.
     */
    @SuppressWarnings({"SameParameterValue"})
    public JMSRuntimeException(String message, Throwable rootcause)
    {
        super(message, rootcause);
    }
}
