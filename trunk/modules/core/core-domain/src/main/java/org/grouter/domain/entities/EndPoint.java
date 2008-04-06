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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * An EndPoint represents the interface for inbound and outbound messaging. A Node can have one
 * inbound Endpoint and one outbound EndPoint.
 * Tied to an EndPoint is a collection of EndPointContext items, keyName/value pairs with both optional
 * keyName/value pairs and keyName/value pairs to override existing default keyName/value pairs in a worker thread -
 * a reader or writer.
 * <p/>
 * Quartz is used to schedule reader and writer jobs (threads) tied to each EndPoint.
 * <p/>
 * An EndPoint can also have a filter - filtering out messages which should not be used.
 *
 * @author Georges Polyzois
 */

@Entity
@Table(name = "endpoint")
public class EndPoint extends BaseEntity
{
    @Id
    @NotNull
    @Column(name = "id", nullable = false)
    @GenericGenerator(name = "system-uuid", strategy = "assigned")
    private String id;

    @Column(name = "clazzname")
    private String clazzName;

    @Column(name = "uri")
    private String uri;

    @Column(name = "cron")
    private String cron;

    // Unidirectional ManyToOne
    @ManyToOne(targetEntity = EndPointType.class)
    @JoinColumn(name = "endpoint_type_fk")
    @NotNull
    EndPointType endPointType;

    @Transient
    transient Filter filter;

    @org.hibernate.annotations.CollectionOfElements  (fetch=FetchType.EAGER)
    @JoinTable( name = "endpoint_context", joinColumns = @JoinColumn(name = "endpoint_fk"))
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @org.hibernate.annotations.MapKey(columns = @Column(name="keyname"))
    @Column(name = "value")
    Map<String,String> endPointContext = new HashMap<String,String>();


    public EndPoint()
    {
    }

    public EndPoint(String id)
    {
        this.id = id;
    }

    public Filter getFilter()
    {
        return filter;
    }

    public void setFilter(Filter filter)
    {
        this.filter = filter;
    }

    public String getCron()
    {
        return cron;
    }

    public void setCron(String cron)
    {
        this.cron = cron;
    }

    public String getClazzName()
    {
        return clazzName;
    }

    public void setClazzName(String clazzName)
    {
        this.clazzName = clazzName;
    }


    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public EndPointType getEndPointType()
    {
        return endPointType;
    }

    public void setEndPointType(EndPointType endPointType)
    {
        this.endPointType = endPointType;
    }

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public Map getEndPointContext()
    {
        return endPointContext;
    }

    public void setEndPointContext(Map endPointContext)
    {
        this.endPointContext = endPointContext;
    }

    public String toString()
    {
        return "EndPoint{" +
                "id='" + id + '\'' +
                ", uri='" + uri + '\'' +
                ", clazzName='" + clazzName + '\'' +
                ", cron='" + cron + '\'' +
                ", endPointType=" + endPointType +
                ", filter=" + filter +
                '}';
    }
}
