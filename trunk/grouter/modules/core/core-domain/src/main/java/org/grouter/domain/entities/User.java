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


import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;


/**
 * Domain entity.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity implements Comparable
{
    public final static int MAX_USER_NAME_LENGTH = 15;
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private User createdBy;
    private Address address;
    private Set<UserRole> roles = new HashSet<UserRole>();
    private Boolean deleted;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "pwd")
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }


    @Column(name = "username")
    @Length(min = 5, max = MAX_USER_NAME_LENGTH)
    //@Pattern(regex="[0-9]+")
    @NotNull
    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public boolean isSuperReviewer()
    {
        return roles.contains(Role.SUPER_REVIEWER);
    }


    public boolean isAdmin()
    {
        return roles.contains(Role.ADMIN);
    }


    public boolean isReviewer()
    {
        return roles.contains(Role.REVIEWER);
    }


    public User getCreatedBy()
    {
        return createdBy;
    }


    public void setCreatedBy(User createdBy)
    {
        this.createdBy = createdBy;
    }


    @OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    public Set<UserRole> getRoles()
    {
        return roles;
    }


    public void setRoles(Set<UserRole> roles)
    {
        this.roles = roles;
    }


    @Column(name = "firstname")
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Column(name = "lastname")
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Column(name = "deleted")
    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }

    /**
     * Sorting inmemory using {@link java.util.Collections} sort will do sort by name.
     *
     * @param anotherObject is a non-null Role.
     * @throws NullPointerException if anotherObject is null.
     * @throws ClassCastException   if anotherObject is not an Role object.
     */
    public int compareTo(Object anotherObject) throws ClassCastException
    {
        //optimizing
        if (this == anotherObject)
        {
            return 0;
        }

        User compareTo = (User) anotherObject;
        return getUserName().compareTo(compareTo.getUserName());
    }

}
