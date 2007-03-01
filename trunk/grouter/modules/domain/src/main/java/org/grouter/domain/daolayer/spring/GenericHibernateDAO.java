package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.GenericDAO;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Implements the generic CRUD data access operations using Hibernate APIs.
 * <p/>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 appraoch for Entity:DAO design.
 * <p/>
 * You have to inject the <tt>Class</tt> object of the persistent class and a current
 * Hibernate <tt>Session</tt> to construct a DAO.
 * <p/>
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss )
 *
 * @author Georges Polyzois
 */
public abstract class GenericHibernateDAO<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, ID>
{
    private Class<T> entityClass;
    private SessionFactory sessionFactory;
    protected Session session;


    protected GenericHibernateDAO(Class<T> persistentClass)
    {
        this.entityClass = persistentClass;
    }

    public GenericHibernateDAO(Class<T> persistentClass, Session session)
    {
        this.entityClass = persistentClass;
        this.session = session;
    }

//    protected abstract void setSession(Session s);


    public Class<T> getEntityClass()
    {
        return entityClass;
    }

   
    public T findById(ID id)
    {
        return (T) getSession().load(getEntityClass(), id);
    }


    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty)
    {
        Criteria crit = getSession().createCriteria(getEntityClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty)
        {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    public T save(T entity)
    {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public void delete(T entity)
    {
        getSession().delete(entity);
    }


    public void delete(ID id)
    {
        getSession().delete(findById(id));
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


    /**
     *
     */
    @SuppressWarnings("unchecked")
    public T findById(Class clazz, T id, String... joinProps)
    {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.idEq(id));
        for (String prop : joinProps)
        {
            criteria.setFetchMode(prop, FetchMode.JOIN);
        }
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return (T) criteria.uniqueResult();
    }


}
