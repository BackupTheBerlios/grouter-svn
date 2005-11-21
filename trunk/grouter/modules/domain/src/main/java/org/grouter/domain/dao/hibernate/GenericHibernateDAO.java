package org.grouter.domain.dao.hibernate;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.grouter.domain.dao.GenericDAO;

import java.util.*;
import java.io.Serializable;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * <p/>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p/>
 * You have to inject the <tt>Class</tt> object of the persistent class and a current
 * Hibernate <tt>Session</tt> to construct a DAO.
 *
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 *
 * @author Georges Polyzois
 */
public class GenericHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID>
{
    private Class<T> entityClass;
    private Session session;

    public GenericHibernateDAO(Class<T> persistentClass, Session session)
    {
        this.entityClass = persistentClass;
        this.session = session;
    }

    protected Session getSession()
    {
        return session;
    }

    public Class<T> getEntityClass()
    {
        return entityClass;
    }

    public T findById(ID id, boolean lock)
    {
        T entity;
        if (lock)
        {
            entity = (T) getSession().load(getEntityClass(), id, LockMode.UPGRADE);
        }
        else
        {
            entity = (T) getSession().load(getEntityClass(), id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance)
    {
        return findByCriteria(Example.create(exampleInstance));
    }

    @SuppressWarnings("unchecked")
    public T saveOrUpdate(T entity)
    {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public void delete(T entity)
    {
        getSession().delete(entity);
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion)
    {
        Criteria crit = getSession().createCriteria(getEntityClass());
        for (Criterion c : criterion)
        {
            crit.add(c);
        }
        return crit.list();
    }

}

