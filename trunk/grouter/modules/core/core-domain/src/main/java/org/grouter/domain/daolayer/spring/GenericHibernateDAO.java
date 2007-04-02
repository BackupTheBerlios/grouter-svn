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
public abstract class GenericHibernateDAO<T, ID extends Serializable> extends HibernateDaoSupport implements GenericDAO<T,ID>
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

    public Class<T> getEntityClass()
    {
        return entityClass;
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings( "unchecked" )
    public T findById( Class clazz, ID id, String... joinProps )
    {
        Criteria criteria = getSession(  ).createCriteria( clazz );
        criteria.add( Restrictions.idEq( id ) );
        for ( String prop : joinProps )
        {
            criteria.setFetchMode( prop, FetchMode.JOIN );
        }
        criteria.setResultTransformer( CriteriaSpecification.DISTINCT_ROOT_ENTITY );
        return ( T ) criteria.uniqueResult(  );
    }
    


    /**
     * {@inheritDoc}
     */
    public T findById(ID id, String... joinProps)
    {
        return findById( getEntityClass(), id, joinProps );
        //return (T) getSession().load(getEntityClass(), id, LockMode.FORCE);
    }



    /**
     * Find Entity by id.
     *
     * @param id the id of the entity
     * @return an entity
     */
    public T findById(ID id)
    {
        return findById( getEntityClass(), id );
    }



    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return getSession(  ).createCriteria( getEntityClass() ).list();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String... joinProps)
    {
        Criteria crit = getSession().createCriteria(getEntityClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : joinProps)
        {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }


    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
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

