/*
 *
 * $HeadURL$
 *
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


import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * Domain entity.
 *
 * @author $Author$ , Georges Polyzois
 * @version $Rev$ $Date$
 */
@Entity
@Table(name = "user")
@Indexed(index = "indexes/user")
// Entity will be indexed for querying using Hibernate SystemServiceImpl
public class User extends BaseEntity
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @DocumentId
    @Boost(2.5f)
    //@NotNull
    private Long id;


    @Column(name = "username")
    @Length(min = 5, max = MAX_USER_NAME_LENGTH)
    @NotNull
    @Field(index = Index.TOKENIZED, store = Store.YES)
    @Boost(2.5f)  // probability to reach the top of the search list will be multiplied by 2.5
    private String userName;

    @Column(name = "firstname")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String firstName;

    @Column(name = "lastname")
    @Field(index = Index.TOKENIZED, store = Store.YES)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "expireson")
    private User expiresOn;

    @Column(name = "remaininglogonattempts")
    private Integer remainingLogonAttempts;

    @ManyToOne
    @JoinColumn(name = "user_state_fk")
    @NotNull
    private UserState userState;

    @ManyToOne
    @JoinColumn(name = "locale_fk")
    private Locale locale;

    @ManyToOne ( cascade = {CascadeType.ALL}, fetch=FetchType.EAGER )
    @JoinColumn(name = "address_fk" )
    @IndexedEmbedded    
    private Address address;

    /*
     * The inverse target property of the target entity. We do not need to enter the foreign
     * key kolumn as we do in the hbm mapping file -> less verbose.
     * mappedBy means inverse="true"  meaning that any changes made to this collection
     * is not persisted. If you want to persist it you need to call node.setRouter(...)
     * on the item in the Set
     *
     * We are using cascade on delete in DB - avoids a delete for every user_role row
     * -> @org.hibernate.annotations.OnDelete(action= OnDeleteAction.CASCADE)
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @org.hibernate.annotations.OnDelete(action= OnDeleteAction.CASCADE)
    private Set<UserRole> userRoles = new HashSet<UserRole>();


    // static initialized types - users that are always creatd in the system
    public static final User SYSTEM = new User(1L);
    public static final User ADMIN = new User(2L);
    private final static Map<Long, User> valueOfMap = new LinkedHashMap<Long, User>(2);
    static
    {
        valueOfMap.put(ADMIN.getId(), ADMIN);
        valueOfMap.put(SYSTEM.getId(), SYSTEM);

    }

    public final static int MAX_USER_NAME_LENGTH = 15;


    public User()
    {
    }

    public User(Long id)
    {
        this.id = id;
    }

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
        return userRoles.contains(Role.SUPER_REVIEWER);
    }


    public boolean isAdmin()
    {
        return userRoles.contains(Role.ADMIN);
    }


    public boolean isReviewer()
    {
        return userRoles.contains(Role.REVIEWER);
    }

    public Set<UserRole> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles)
    {
        this.userRoles = userRoles;
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
