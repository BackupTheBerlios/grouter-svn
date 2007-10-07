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
 * Domain entity.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity   //implements Comparable
{
    private Long id;
    private User user;
    private Role role;

    public UserRole()
    {
    }

    public UserRole(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue( strategy=GenerationType.AUTO )
    @NotNull
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    public User getUser()
    {
        return user;
    }


    public void setUser(User user)
    {
        this.user = user;
    }


    public Role getRole()
    {
        return role;
    }


    public void setRole(Role role)
    {
        this.role = role;
    }


    /**
     * Sorting inmemory using Collections.sort will do sort by message.
     *
     * @param anotherObject is a non-null Role.
     * @throws NullPointerException if anotherObject is null.
     * @throws ClassCastException   if anotherObject is not an Role object.
     */
/*    public int compareTo(Object anotherObject) throws ClassCastException
    {
        if (this == anotherObject)
        {
            return 0;
        }

        UserRole compareTo = (UserRole) anotherObject;
        return getId().compareTo(compareTo.getId());
    }
  */
}
