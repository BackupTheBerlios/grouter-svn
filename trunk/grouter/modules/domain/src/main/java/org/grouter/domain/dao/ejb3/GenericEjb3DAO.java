package org.grouter.domain.dao.ejb3;

import org.grouter.domain.dao.GenericDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;


import javax.persistence.*;
import java.util.*;
import java.io.Serializable;

/**
 * Implements the generic CRUD data access operations using EJB3 APIs.
 * <p/>
 * To write a DAO, subclass and parameterize this class with your entity.
 * Of course, assuming that you have a traditional 1:1 appraoch for
 * Entity:DAO design. This is actually an implementation that uses some
 * extensions for EJB3 persistence from Hibernate - you can see how the
 * packages for the extensions are not imported, but named inline.
 */
public abstract class GenericEjb3DAO<T, ID extends Serializable> implements GenericDAO<T, ID>
{
    private Class persistentClass;
    @PersistenceContext(unitName = PersistenceContextName.PERSISTENCE)
    EntityManager em;

    public GenericEjb3DAO(Class persistentClass)
    {
        this.persistentClass = persistentClass;
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
    public T findById(ID id, boolean lock)
    {
        /*
        T entity;
        if (lock)
        {
            entity = (T) ((org.hibernate.ejb.HibernateEntityManager) em).getSession()
                    .load(getPersistentClass(), id, org.hibernate.LockMode.UPGRADE);
        } else
        {
            entity = (T) em.find(getPersistentClass(), id);
        }
        return entity;*/
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return em.createQuery("from " + getPersistentClass()).getResultList();
    }


    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty)
    {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        /*      Criteria crit = ((org.hibernate.HibernateEntityManager)em).getSession()
                            .createCriteria(getPersistentClass());
        Example example =  Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
        */

        return null;
    }

    public T makePersistent(T entity)
    {
        return em.merge(entity);
    }

    public void makeTransient(T entity)
    {
        em.remove(entity);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(org.hibernate.criterion.Criterion... criterion)
    {
        // Using Hibernate, more difficult with EntityManager and EJB-QL
        /*  org.hibernate.Session session = ((org.hibernate.ejb.HibernateEntityManager)em).getSession();
       org.hibernate.Criteria crit = session.createCriteria(getPersistentClass());
       for (org.hibernate.criterion.Criterion c : criterion) {
           crit.add(c);
       }
       return crit.list();*/
        return null;
    }

}

