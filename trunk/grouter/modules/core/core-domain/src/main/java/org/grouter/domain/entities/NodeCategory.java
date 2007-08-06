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

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


public class NodeCategory implements Comparable
{
    Long id;
    public static final long ROOT_CATEGORY_ID = 1;

    // Top level categories
    public static final Long MAIL_NODES = 1000000L;
    public static final Long WS_NODES = 2000000L;

    private long position;
    private NodeCategory parent;
    private String name;
    private List<NodeCategory> subCategories = new ArrayList<NodeCategory>();
    private transient boolean leaf;
    private transient String displayName;

    public NodeCategory()
    {
    }


    public NodeCategory(Long id)
    {
        this.id = id;
    }

    public NodeCategory getParent()
    {
        return parent;
    }


    public void setParent(NodeCategory parent)
    {
        this.parent = parent;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public boolean isMailNode()
    {
        return id.equals(MAIL_NODES);
    }


    public boolean isRoot()
    {
        return (id != null) && id.equals(ROOT_CATEGORY_ID);
    }


    public boolean isParentRoot()
    {
        return (parent != null) && (parent.id.equals(ROOT_CATEGORY_ID));
    }


    public List<NodeCategory> getSubCategories()
    {
        return subCategories;
    }


    public void setSubCategories(List<NodeCategory> subCategories)
    {
        this.subCategories = subCategories;
    }


    public long getPosition()
    {
        return position;
    }


    public void setPosition(long position)
    {
        this.position = position;
    }


    public boolean isLeaf()
    {
        return leaf;
    }


    public void setLeaf(boolean leaf)
    {
        this.leaf = leaf;
    }


    public String getDisplayName()
    {
        return displayName;
    }


    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }


    /**
     * Convenience method, for the bi-directional parent/child relation
     *
     * @param child
     */
    public void addSubCategory(NodeCategory child)
    {
        getSubCategories().add(child);
        child.setParent(this);
    }


    /**
     * Sorting using Collections.sort will do sort by name.
     *
     * @param anotherCategory is a non-null Category.
     * @throws NullPointerException if anotherCategory is null.
     * @throws ClassCastException   if anotherCategory is not an Category object.
     */
    public int compareTo(Object anotherCategory) throws ClassCastException
    {
        //optimizing
        if (this == anotherCategory)
        {
            return 0;
        }

        NodeCategory compareToCategory = (NodeCategory) anotherCategory;
        int comparison = getName().compareTo(compareToCategory.getName());

        if (comparison != 0)
        {
            return comparison;
        } else
        {
            return 0;
        }
    }
}
