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
import java.util.*;

/**
 * @author Georges Polyzois
 */

@Entity
@Table(name = "locale")
public class Locale extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    private Long id;

    @Column(name = "language")
    private String language;

    @Column(name = "country")
    private String country;

    @Column(name = "sort")
    private Long sort;

    public static Locale EN_UK = new Locale(1L, "English", "UK", 1L);
    public static Locale SW_SE = new Locale(2L, "English", "UK", 2L);

    public static Map<Long, Locale> values = new HashMap<Long, Locale>();

    public Locale(final Long id, final String language, final String country, final Long sortOrder
    )
    {
        this.id = id;
        this.language = language;
        this.country = country;
        this.sort = sortOrder;
    }

    static
    {
        values.put(EN_UK.getId(), EN_UK);
        values.put(SW_SE.getId(), SW_SE);
    }

    public static Locale valueOf(Long id)
    {
        return values.get(id);
    }

    /**
     * Util method.
     *
     * @return lsit of NodeStatus
     */
    public static List<Locale> values()
    {
        return Collections.unmodifiableList(new ArrayList<Locale>(values.values()));
    }

    public Locale()
    {
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(final String language)
    {
        this.language = language;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(final String country)
    {
        this.country = country;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }
}
