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

import org.apache.log4j.Logger;
import org.hibernate.validator.NotNull;

import javax.persistence.*;


/**
 * Domain class.
 *
 * @Author Georges Polyzois
 */
@Entity
@Table(name = "jndi")
public class SettingsContext extends BaseEntity<Long>
{
    // These must match names specified in config.xsd
    public final static String KEY_SETTINGS_JNDI_JAVA_NAMING_FACTORY_INITIAL = "naming.factory.initial";
    public final static String KEY_SETTINGS_JNDI_JAVA_NAMING_PROVIDER_URL = "naming.provider.url";
    public final static String KEY_SETTINGS_JNDI_JAVA_NAMING_FACTORY_URL = "naming.factory.url";
    public final static String KEY_SETTINGS_JNDI_QUEUECONNECTIONFACTORY = "naming.queueconnectionfactoy";
    public final static String KEY_SETTINGS_LOGGING_JMSLOGGINGQUEUE = "jms.loggingQueue";



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    Long id;

    @Column(name = "keyname")
    String keyname;

    @Column(name = "value")
    String value;

    @Column(name = "endpoint_fk")
    Settings settings;


    public SettingsContext(Long id, String keyname, String value)
    {
        this.id = id;
        this.keyname = keyname;
        this.value = value;
    }


    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getKeyname()
    {
        return keyname;
    }

    public void setKeyname(String keyname)
    {
        this.keyname = keyname;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }


    @ManyToOne(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinColumn(name = "endpoint_fk", nullable = false)
    public Settings getSettings()
    {
        return settings;
    }

    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }





}