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
import java.util.Map;
import java.util.HashMap;

/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@Entity
@Table(name = "settings")
public class Settings //extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    @NotNull
    private String id;


    //@OneToMany ( mappedBy = "settings", targetEntity = SettingsContext.class,   cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Transient
    Map settingsContext = new HashMap();

    public Settings()
    {
    }

    public Settings(final String id, final Map<String,String> settingsContext)
    {
        this.id = id;
        this.settingsContext = settingsContext;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public Map getSettingsContext()
    {
        return settingsContext;
    }

    public void setSettingsContext(final Map settingsContext)
    {
        this.settingsContext = settingsContext;
    }
}