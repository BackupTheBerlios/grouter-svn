package org.grouter.common.hibernate;

import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * HibernateUtil but for
 */
public class EntityManagerUtil
{
    private static EntityManagerFactory emf;
    public static final ThreadLocal<EntityManager> entitymanager = new ThreadLocal<EntityManager>();
    private static final String GROUTER_DOMAIN = "grouterDomain";

    public static EntityManagerFactory getEntityManagerFactory()
    {
        if (emf == null)
        {
            // Create the EntityManagerFactory
            emf = Persistence.createEntityManagerFactory(GROUTER_DOMAIN);
        }

        return emf;
    }


    /**
     * Corresponds to a Hibernate Session.
     *
     * @return
     */
    public static EntityManager getEntityManager()
    {
        EntityManager em = entitymanager.get();

        // Create a new EntityManager
        if (em == null)
        {
            em = emf.createEntityManager();
            entitymanager.set(em);
        }
        return em;
    }


    /**
     * Close our "session".
     */
    public static void closeEntityManager()
    {
        EntityManager em = entitymanager.get();
        entitymanager.set(null);
        if (em != null) em.close();
    }

}

