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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class UserState extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    Long id;

    @Column(name = "name")
    String name;

    // createdOn
    public static UserState NEW = new UserState(1L, "NEW");
    // avaiting activation - user received One Time Password and needs to change it
    public static UserState ACTIVATION_PENDING = new UserState(3L, "ACTIVATION_PENDIN");
    // user activated
    public static UserState ACTIVE = new UserState(2L, "ACTIVE");
    // if retries exceeds max number of retries or if manually set to blocked by super user
    public static UserState BLOCKED = new UserState(4L, "BLOCKED");

    public static Map<Long, UserState> values = new HashMap<Long, UserState>();

    static
    {
        values.put(NEW.getId(), NEW);
        values.put(ACTIVATION_PENDING.getId(), ACTIVATION_PENDING);
        values.put(ACTIVE.getId(), ACTIVE);
        values.put(BLOCKED.getId(), BLOCKED);
    }


    public UserState()
    {
    }

    public UserState(final Long id, final String name)
    {
        this.id = id;
        this.name = name;
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
