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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "role")
public class Role extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-long")
    @GenericGenerator(name = "system-long", strategy = "assigned")
    @NotNull
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "displayname")
    private String displayName;

    // static initialized types
    public static final Role ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role REVIEWER = new Role(2L, "ROLE_REVIEWER");
    public static final Role SUPER_REVIEWER = new Role(3L, "ROLE_SUPER_REVIEWER");
    public static final Role EDITOR = new Role(4L, "ROLE_EDITOR");

    private final static Map<Long, Role> valueOfMap = new LinkedHashMap<Long, Role>(4);

    static
    {
        valueOfMap.put(ADMIN.getId(), ADMIN);
        valueOfMap.put(REVIEWER.getId(), REVIEWER);
        valueOfMap.put(SUPER_REVIEWER.getId(), SUPER_REVIEWER);
        valueOfMap.put(EDITOR.getId(), EDITOR);
    }

    public Role()
    {
    }

    private Role(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public static Role valueOf(Long id)
    {
        return valueOfMap.get(id);
    }

    public static List<Role> values()
    {
        return new ArrayList<Role>(valueOfMap.values());
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }


    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Sorting inmemory using Collections.sort will do sort by id.
     *
     * @param anotherRole is a non-null Role.
     * @throws NullPointerException if anotherRole is null.
     * @throws ClassCastException   if anotherRole is not an Role object.
     */
    public int compareTo(Object anotherRole) throws ClassCastException
    {
        //optimizing
        if (this == anotherRole)
        {
            return 0;
        }

        Role compareToRole = (Role) anotherRole;
        return getId().compareTo(compareToRole.getId());
    }

}
