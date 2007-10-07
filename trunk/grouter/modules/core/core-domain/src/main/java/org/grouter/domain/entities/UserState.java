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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Georges Polyzois
 */

@Entity
@Table(name = "user_state")
public class UserState
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    Long id;

    @Column(name = "name")
    String name;

    // created
    public static EndPointType NEW = new EndPointType(1L, "NEW");
    // avaiting activation - user received One Time Password adn needs to change it
    public static EndPointType ACTIVATION_PENDING = new EndPointType(3L, "ACTIVATION_PENDIN");
    // user activated
    public static EndPointType ACTIVE = new EndPointType(2L, "ACTIVE");
    // if retries exceeds max number of retries or if manually set to blocked by super user
    public static EndPointType BLOCKED = new EndPointType(4L, "BLOCKED");

    public static Map<Long, EndPointType> values = new HashMap<Long, EndPointType>();

    static
    {
        values.put(NEW.getId(), NEW);
        values.put(ACTIVATION_PENDING.getId(), ACTIVATION_PENDING);
        values.put(ACTIVE.getId(), ACTIVE);
        values.put(BLOCKED.getId(), BLOCKED);
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }
}
