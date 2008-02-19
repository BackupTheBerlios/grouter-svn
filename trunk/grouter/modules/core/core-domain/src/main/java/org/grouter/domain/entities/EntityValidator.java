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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;


/**
 * Helper for validating an entity instance manually, e.g. in an application layer
 * <p/>
 * Hibernate will automagically do validation if it finds entity classes mapped with Hibernata Validation
 * annotations. From documentation:
 * "Hibernate Validator has two built-in Hibernate event listeners. Whenever a PreInsertEvent or PreUpdateEvent
 * occurs, the listeners will verify all constraints of the entity instance and throw an exception if any constraint
 * is violated. Basically, objects will be checked before any inserts and before any updates made by Hibernate."
 * To avoid this (why?) use:
 * hibernate.validator.autoregister_listeners=false if not tovlidate on preInsert or preUpdate events.
 * <p/>
 * ClassValidator are recommended to be cached -> static declared in this helper.
 *
 * @author Georges
 */
public class EntityValidator
{
    private static Log log = LogFactory.getLog(EntityValidator.class);

    public static final ClassValidator<Router> ROUTER_VALIDATOR = new ClassValidator<Router>(Router.class);
    public static final ClassValidator<Node> NODE_VALIDATOR = new ClassValidator<Node>(Node.class);

    /**
     * Helper.
     *
     * @param clazz
     * @return a ClassValidator for a given entity class
     */
    private static ClassValidator<? extends BaseEntity> getValidator(Class<? extends BaseEntity> clazz)
    {
        if (Router.class.equals(clazz))
        {
            return null;//ROUTER_VALIDATOR;
        } else if (Node.class.equals(clazz))
        {
            return null;//NODE_VALIDATOR;
        } else
        {
            throw new IllegalArgumentException("Unsupported class was passed.");
        }
    }

    /**
     * Validates an entity.
     *
     * @param modelObject
     * @return
     */
    public static InvalidValue[] validate(BaseEntity modelObject)
    {
        return validate(modelObject, null);
    }

    /**
     * VAlidates an entity for a given property.
     *
     * @param modelObject entity
     * @param property    attribute of entity we want to validate
     * @return
     */
    public static InvalidValue[] validate(BaseEntity modelObject,
                                          String property)
    {
        Class<? extends BaseEntity> clazz = modelObject.getClass();
        ClassValidator validator = getValidator(clazz);

        InvalidValue[] validationMessages;

        if (property == null)
        {
            validationMessages = validator.getInvalidValues(modelObject);
        } else
        {
            validationMessages = validator.getInvalidValues(modelObject, property);
        }
        return validationMessages;
    }
}