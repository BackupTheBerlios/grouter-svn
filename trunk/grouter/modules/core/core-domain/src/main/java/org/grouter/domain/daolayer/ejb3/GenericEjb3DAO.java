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

package org.grouter.domain.daolayer.ejb3;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.grouter.domain.daolayer.GenericDAO;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.jpa.FullTextEntityManager;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the generic CRUD data access operations using EJB3 APIs.
 * To write a DAO, subclass and parameterize this class with your entity.
 * Of course, assuming that you have a traditional 1:1 appraoch for
 * Entity:DAO design. This is actually an implementation that uses some
 * extensions for EJB3 persistence from Hibernate - you can see how the
 * packages for the extensions are not imported, but named inline.
 * <p/>
 * See Christian Bauers generic DAO at Hibernate.org - the Caveat example applications.
 *
 * @author Georges Polyzois
 */
public abstract class GenericEjb3DAO<T, ID extends Serializable> implements GenericDAO<T, ID>
{
    private static Logger logger = Logger.getLogger(GenericEjb3DAO.class);
    private Class persistentClass;

    @PersistenceContext( unitName = PersistenceContextName.PERSISTENCE)
    EntityManager em;

    static InitialContext initialContext;

    // OR DO THIS
    //  @Resource(mappedName="java:/grouterDomain")
    //  EntityManager em;
    //  check persistence.xml for config where EM is registered to jndi


    public GenericEjb3DAO(Class persistentClass)
    {
        this.persistentClass = persistentClass;
    }


    private EntityManager getEntityManager()
    {
        // CAn not get the injection to work properly...
        if (em == null)
        {
            try
            {
                initialContext = new InitialContext();  //JNDIUtils.getJbossInitialContext();
                Object obje = initialContext.lookup(PersistenceContextName.JNDI_ENTITYMANAGER);
                em = (EntityManager) obje;
            } catch (NamingException e)
            {
                e.printStackTrace();
            }
            return em;
        }
        // we are in deep sh--t
        return null;

    }


    public void setEm(EntityManager em)
    {
        this.em = em;
    }

    public Class getPersistentClass()
    {
        return persistentClass;
    }


    @SuppressWarnings("unchecked")
    public T findById(final ID id, final boolean lock)
    {
        T entity;
        if (lock)
        {
            entity = (T) ((HibernateEntityManager) getEntityManager()).getSession()
                    .load(getPersistentClass(), id, org.hibernate.LockMode.UPGRADE);
        } else
        {
            entity = (T) em.find(getPersistentClass(), id);
        }
        return entity;
    }


    /**
     * {@inheritDoc}
     */
    public T findById(final ID id, final String... joinProps)
    {
        org.hibernate.Session session = ((HibernateEntityManager) getEntityManager()).getSession();
        org.hibernate.Criteria criteria = session.createCriteria(getPersistentClass());
        criteria.add(Restrictions.idEq(id));
        for (String prop : joinProps)
        {
            criteria.setFetchMode(prop, FetchMode.JOIN);
        }
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (T) criteria.uniqueResult();
    }

    public T findById(final ID id)
    {
        return (T) em.find(getPersistentClass(), id);
    }


    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return getEntityManager().createQuery("from " + getPersistentClass()).getResultList();
    }


    public List<T> findAll(final String hql)
    {
        return getEntityManager().createQuery(hql).getResultList();
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByExample(final T exampleInstance, final String... excludeProperty)
    {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        Criteria crit = ((HibernateEntityManager) getEntityManager()).getSession()
                .createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty)
        {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    public T save(T entity)
    {
        return getEntityManager().merge(entity);
    }


    public void delete(T entity)
    {
        getEntityManager().remove(entity);
    }


    public void delete(ID id)
    {
        getEntityManager().remove(findById(id, true));
    }


    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(org.hibernate.criterion.Criterion... criterion)
    {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        org.hibernate.Session session = ((HibernateEntityManager) getEntityManager()).getSession();
        org.hibernate.Criteria crit = session.createCriteria(getPersistentClass());
        for (org.hibernate.criterion.Criterion c : criterion)
        {
            crit.add(c);
        }
        return crit.list();
    }


    @SuppressWarnings("unchecked")
    public T findById(final Class clazz, final T id, final String... joinProps)
    {
        org.hibernate.Session session = ((HibernateEntityManager) getEntityManager()).getSession();
        org.hibernate.Criteria criteria = session.createCriteria(getPersistentClass());
        criteria.add(Restrictions.idEq(id));
        for (String prop : joinProps)
        {
            criteria.setFetchMode(prop, FetchMode.JOIN);
        }
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (T) criteria.uniqueResult();
    }


    

    public void purge(final T entity, final ID id)
    {
        throw new UnsupportedOperationException("Not implemented yet");
        /*FullTextEntityManager fullTextSession = Search.createFullTextSession(getSession());
        Transaction tx = fullTextSession.beginTransaction();
        fullTextSession.purge( entity, id );
        tx.commit();*/
    }



    public void optimizeIndex(final T entity)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * We remove this entity from the query index.
     *
     * @param entity entity to remove
     */
    public void purgeFromIndex(final T entity, final ID id)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    public List<T> findFromIndex(final String queryString, final String... columns)
    {

        List<T> list = new ArrayList<T>();

        String[] queryColumns = new String[columns.length];
        int i = 0;
        for (String column : columns)
        {
            queryColumns[i] = column;
            i++;
        }


        EntityManager em = getEntityManager();
        FullTextEntityManager fullTextEntityManager =
        org.hibernate.search.jpa.Search.createFullTextEntityManager(em);
        MultiFieldQueryParser parser = new MultiFieldQueryParser( queryColumns , new StandardAnalyzer());
        Query query = null;
        try
        {
            query = parser.parse( queryString );
            org.hibernate.Query hibQuery = (org.hibernate.Query) fullTextEntityManager.createFullTextQuery( query, getPersistentClass() );
            list = hibQuery.list();


        } catch (ParseException e)
        {
            logger.warn("Faile to query for :" + queryString);
        }

        return list;
    }



}

