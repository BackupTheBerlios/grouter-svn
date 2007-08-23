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
package org.grouter.domain.daolayer;


import java.io.Serializable;
import java.util.List;

/**
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 *
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 * Also see this link : http://www.hibernate.org/328.html
 *
 * @author Georges Polyzois
 */
public interface GenericDAO<T, ID extends Serializable>
{
    /**
     * Find Entity by id.
     *
     * @param id the id of the entity
     * @return an entity
     */
    T findById(ID id);

    /**
     * Find entity by id, join using properties of entity class (optionally).
     *
     * @param id        the id of the entity to search for
     * @param joinProps properties on the entity class we should join in
     * @return T
     */
    T findById(ID id, String... joinProps);

    /**
     * Find entity by id and clazz, join using properties of entity class (optionally).
     *
     * @param clazz     entity class
     * @param id        the id of the entity to search for
     * @param joinProps properties on the entity class we should join in
     * @return te enity found, or null if none found
     */
    T findById(Class clazz, T id, String... joinProps);

    /**
     * Get all entities.
     *
     * @return list of entities.
     */
    List<T> findAll();

    /**
     * Find entities, excluding properties. Provide an instance of the queried class with some properties initialized,
     * and the query returns all persistent instances with matching property values.
     * <p/>
     * E.g.
     * Router example = new Router();
     * return routerDAO.findByExample( example, "nodes" , "upTime");
     *
     * @param exampleInstance an example entity instance
     * @param excludeProperty exceluding these properties, and thereby not joining in those
     * @return list with entities matching the example provided
     */
    List<T> findByExample(T exampleInstance, String... excludeProperty);

    /**
     * Save and persiste the entity.
     *
     * @param entity enitty to persist
     * @return the persisted entity (with id set)
     */
    T save(T entity);

    /**
     * Mark for deletion on next session flush.
     *
     * @param entity the enitty to delete
     */
    void delete(T entity);

    /**
     * Mark for deletion on next session flush.
     *
     * @param id the id of the enitty to delete
     */
    void delete(ID id);
}
