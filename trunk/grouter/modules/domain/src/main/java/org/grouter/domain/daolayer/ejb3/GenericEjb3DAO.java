package org.grouter.domain.daolayer.ejb3;

import org.grouter.domain.daolayer.GenericDAO;
import org.hibernate.Criteria;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.criterion.Example;
import org.apache.log4j.Logger;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;


import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Implements the generic CRUD data access operations using EJB3 APIs.
 * To write a DAO, subclass and parameterize this class with your entity.
 * Of course, assuming that you have a traditional 1:1 appraoch for
 * Entity:DAO design. This is actually an implementation that uses some
 * extensions for EJB3 persistence from Hibernate - you can see how the
 * packages for the extensions are not imported, but named inline.
 *
 * See Christian Bauers generic DAO at Hibernate.org - the Caveat example applications.
 *
 * @author Georges Polyzois
 */
public abstract class GenericEjb3DAO<T, ID extends Serializable> implements GenericDAO<T, ID>
{
    private static Logger logger = Logger.getLogger(GenericEjb3DAO.class);
    private Class persistentClass;

    @PersistenceContext(unitName = PersistenceContextName.PERSISTENCE)
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
    public T findById(ID id, boolean lock)
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

    @SuppressWarnings("unchecked")
    public List<T> findAll()
    {
        return getEntityManager().createQuery("from " + getPersistentClass()).getResultList();
    }


    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty)
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

    public T makePersistent(T entity)
    {
        return getEntityManager().merge(entity);
    }

    public void makeTransient(T entity)
    {
        getEntityManager().remove(entity);
    }

    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(org.hibernate.criterion.Criterion... criterion)
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

}

