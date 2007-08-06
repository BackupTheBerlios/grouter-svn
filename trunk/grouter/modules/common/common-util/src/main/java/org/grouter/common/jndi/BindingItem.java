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
package org.grouter.common.jndi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A dto used by {@link JNDIUtils} holding information about implementation and where
 * to bindthe implementation in the jndi providers tree.
 *
 * @author Georges Polyzois
 * @see JNDIUtils
 */
public class BindingItem
{
    private String jndiName;
    private Object implemenation;
    private String[] jndipath;


    /**
     * E.g. consideer binding a sessionfactory (jndiname) to java:env/comp/mbeans (jndipath).
     * The jndipath provided should then look like :
     * String[] jndpath = {"java:env","comp/mbeans"} and jndiname could be "mysessionfactory"
     * and implementation would be Object implemenation = new SessionFactory(). A mocked
     * sessionfactory could come in handyfor test case scenarios.
     *
     *
     * @param jndiName name of object bound to a jndipath
     * @param implemenation a instance with provides some service
     * @param jndipath the path to the jndi bound instance, can only be of length 2
     */
    public BindingItem(String jndiName, Object implemenation, String[] jndipath)
    {
        if(jndipath.length >2)
        {
            throw new IllegalArgumentException("Only two elements allowed. Found : " + jndipath.length +  " comp and env, e.g. {\"java:comp\",\"mbeans\"}");
        }
        this.jndiName = jndiName;
        this.implemenation = implemenation;
        this.jndipath = jndipath;
    }

    public String getJndiName()
    {
        return jndiName;
    }


    public Object getImplemenation()
    {
        return implemenation;
    }


    public String[] getJndipath()
    {
        return jndipath;
    }

    /**
     * Will return something like java:comp/mbeans/mybean
     * @return
     */
    public String getFullJnidPath()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.jndipath[0] );
        stringBuilder.append("/");
        stringBuilder.append(this.jndipath[1] );
        stringBuilder.append("/");
        stringBuilder.append(this.jndiName);
        return stringBuilder.toString();
    }
}
