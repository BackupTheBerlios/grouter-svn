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

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Domain entity.
 *
 *
 *
 * $Date$
 *
 * @author $Author$ , Georges Polyzois
 * @version  $Revision$  Lastchanged: $LastChangedDate$
 * @since $Created$
 *
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity implements Comparable
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column(name = "username")
    @Length(min = 5, max = MAX_USER_NAME_LENGTH)
    @NotNull
    private String userName;

    @Column(name = "firstname")
    @NotNull
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    private String lastName;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "createdby")
    @NotNull
    private User createdBy;

    @Column(name = "createdon")
    private Date createdOn;

    @Column(name = "modifiedon")
    private Date modifiedOn;

    @Column(name = "expireson")
    private User expiresOn;

    @Column(name = "remaininglogonattempts")
    private Integer remainingLogonAttempts;

    @ManyToOne
    @JoinColumn(name = "user_state_fk")
    private UserState userState;

    @ManyToOne
    @JoinColumn(name = "locale_fk")
    private Locale locale;


    @ManyToOne
    @JoinColumn(name = "address_fk")
    private Address address;

    @OneToMany(cascade = {CascadeType.ALL, CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<UserRole> roles = new HashSet<UserRole>();


    public final static int MAX_USER_NAME_LENGTH = 15;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


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


    public Set<UserRole> getRoles()
    {
        return roles;
    }


    public void setRoles(Set<UserRole> roles)
    {
        this.roles = roles;
    }


    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    public UserState getUserState()
    {
        return userState;
    }

    public void setUserState(final UserState userState)
    {
        this.userState = userState;
    }


    public Date getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(final Date createdOn)
    {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn()
    {
        return modifiedOn;
    }

    public void setModifiedOn(final Date modifiedOn)
    {
        this.modifiedOn = modifiedOn;
    }

    public User getExpiresOn()
    {
        return expiresOn;
    }

    public void setExpiresOn(final User expiresOn)
    {
        this.expiresOn = expiresOn;
    }

    public Integer getRemainingLogonAttempts()
    {
        return remainingLogonAttempts;
    }

    public void setRemainingLogonAttempts(final Integer remainingLogonAttempts)
    {
        this.remainingLogonAttempts = remainingLogonAttempts;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public void setLocale(final Locale locale)
    {
        this.locale = locale;
    }

    /**
     * Sorting inmemory using {@link java.util.Collections} sort will do sort by message.
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
