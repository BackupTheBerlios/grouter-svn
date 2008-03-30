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

package org.grouter.domain.dao.ejb3;

import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.UserDAO;
import org.grouter.domain.dao.spring.UserDAOImpl;


/**
 * All DAOs for ejb based access are declared in here.
 *
 * @author Georges Polyzois
 */
public class Ejb3DAOFactory extends DAOFactory
{

    // Add your DAO interfaces below here
    public MessageDAO getMessageDAO()
    {
        return new MessageDAOBean();
    }

    public UserDAO getSystemUserDAO()
    {
        return new UserDAOImpl();
    }
}
