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

package org.grouter.domain.service.spring.logging;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;

/**
 * @author Georges Polyzois
 */
public interface LogStrategy
{
    /**
     * Log a message handled.
     *
     * @param message
     */
    void log(Message message);

    /**
     * Used by writers / readers to update Node's status information.
     *
     * @param node
     */
    void log(Node node);

}
