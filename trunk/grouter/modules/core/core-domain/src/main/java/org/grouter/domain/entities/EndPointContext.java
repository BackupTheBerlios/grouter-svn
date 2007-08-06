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

import org.hibernate.validator.NotNull;

import javax.persistence.*;

/**
 * Context store - used to override EndPoint default settings. Holds key value pairs used by
 * {@link EndPoint}:s.
 *
 * @author Georges Polyzois
 */

@Entity
@Table(name = "endpoint_context")
public class EndPointContext  extends BaseEntity
{
    Long id;
    String keyname;
    String value;
    EndPoint endPoint;


    public EndPointContext(EndPoint endPoint)
    {
        this.endPoint = endPoint;
    }

    public EndPointContext(String keyname, String value, EndPoint endPoint)
    {
        this.keyname = keyname;
        this.value = value;
        this.endPoint = endPoint;
    }

    EndPointContext()
    {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    public Long getId()
    {
        return id;
    }

    /**
     * Assigned id - no need to expose this setter.
     *
     * @param id
     */
    private void setId(Long id)
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
    public EndPoint getEndPoint()
    {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint)
    {
        this.endPoint = endPoint;
    }


}
