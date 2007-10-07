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

package org.grouter.domain.entities;

import org.hibernate.LazyInitializationException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Base class for Domain entities.
 *
 * @author Georges Polyzois
 */
// public class BaseEntity<ID extends Serializable> implements Serializable   Could not get this to work
public class BaseEntity<ID extends Serializable> implements Serializable
{
    @Transient
    private static Logger logger = Logger.getLogger(BaseEntity.class);

   // private ID id;

    /**
     * A convenience method for outputting all attributes in the instances' state.
     *
     * @return a string with message, value pairs, {@link org.apache.commons.lang.builder.ToStringBuilder#reflectionToString(Object)}
     * @throws LazyInitializationException if the entity was not fully initialized, eg. if you try
     *                                     to print out state of instance while decoupled and instance was not loaded completely
     */
    public String reflectionToString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    /*public ID getId()
    {
        return id;
    }

    public void setId(final ID id)
    {
        this.id = id;
    }*/



}
