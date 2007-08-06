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
import java.util.LinkedHashMap;

/**
 * @author Georges Polyzois
 */
@SuppressWarnings({"PersistenceModelORMInspection"})
@Entity
@Table(name = "node_type")
public class NodeType extends BaseEntity
{
    private Long id;

    private Long position;
    private String name;
    private String displayName;

    public static final NodeType FILE_TO_FILE = new NodeType(1L, "File to file", "File to file", 1L);
    public static final NodeType FILE_TO_EMAIL = new NodeType(2L, "File to email", "File to email", 2L);

    private static Map<Long, NodeType> valueOfMap = new LinkedHashMap<Long, NodeType>(2);

    static
    {
        valueOfMap.put(NodeType.FILE_TO_FILE.getId(), NodeType.FILE_TO_FILE);
        valueOfMap.put(NodeType.FILE_TO_EMAIL.getId(), NodeType.FILE_TO_EMAIL);
    }

    public NodeType()
    {

    }


    public NodeType(Long id, String name, String displayName, Long position)
    {
        this.id = id;
        this.position = position;
        this.name = name;
        this.displayName = displayName;
    }


    public Long getPosition()
    {
        return position;
    }

    public void setPosition(Long position)
    {
        this.position = position;
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


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
