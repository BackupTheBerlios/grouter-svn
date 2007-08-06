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


package org.grouter.common.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;

/**
 * Template method pattern hiding skeleton code of opening closing sessions
 * etc. Use from all Hibernate DAO implementation classes to centralize exception
 * handling and session opening and closing.
 *
 * @author Georges Polyzois
 *
 */
public class HibernateTemplateHandler
{ 
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(HibernateTemplateHandler.class);
    /**
     * Hibernate util instance.
     */
    HibernateUtil hibernateUtil;

    /**
     * Used to hide all hideous exception handling and encapsulating all
     * MUST DO things like closing sessions and gracefully handing back
     * resources.
     *
     * @param handler HibernateHandler
     * @throws java.lang.Exception if session, transaction or mapping failes
     * @return Object
     */
    public Object accept(HibernateTemplate handler) throws Exception
    {
        Object result = null;
        Session session = hibernateUtil.getSession();
        try
        {
            hibernateUtil.beginTransaction();
            result = handler.handle(session);
            session.flush();
            hibernateUtil.commitTransaction();
        }
        catch (MappingException ex)
        {
            hibernateUtil.rollbackTransaction();
            throw new Exception("Mapping failure in Hibernate", ex);
        }
        catch (HibernateException ex)
        {
            hibernateUtil.rollbackTransaction();
            throw new Exception("Transaction rollbacked. Hibernate reported exception.", ex);
        }
        catch (Exception ex)
        {
            hibernateUtil.rollbackTransaction();
            throw new Exception("Transaction rollbacked. Exception caught.", ex);
        }
        finally
        {
            hibernateUtil.closeSession();
        }
        return result;
    }
}
