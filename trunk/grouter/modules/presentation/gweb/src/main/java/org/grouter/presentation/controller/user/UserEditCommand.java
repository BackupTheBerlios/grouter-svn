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

package org.grouter.presentation.controller.user;

import org.grouter.domain.entities.User;
import org.grouter.domain.entities.Address;

/**
 * @author Georges Polyzois
 */
public class UserEditCommand
{
    private User user = new User();
    private Address address = new Address();

    public UserEditCommand(User user)
    {
        this.user = user;
        if( user.getAddress() == null )
        {
            user.setAddress( address );
        }

        //this.user.setAddress( address );
    }


    public UserEditCommand()
    {
        //this.user.setAddress( address );

    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }
}
