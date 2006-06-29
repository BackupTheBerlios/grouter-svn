/**
 * HibernateUtil.java
 */
package org.grouter.common.hibernate;


import org.apache.log4j.Logger;
//import org.hibernate.SessionFactory;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * Basic Hibernate helper class, handles SessionFactory, Session and Transaction.
 *
 * Uses a static initializer for the initial SessionFactory creation
 * and holds Session and Transactions in thread local variables. All
 * exceptions are wrapped in an unchecked InfrastructureException.
 *
 * @author christian@hibernate.org
 * @author Georges Polyzois
 */
public class HibernateUtil
{
    /** Logger.      */
    private static Logger log = Logger.getLogger(HibernateUtil.class);
    /** configuration.   */
    private static Configuration configuration;
    /** threadlocal.     */
    private static final ThreadLocal THREADSESSION = new ThreadLocal();
    /** threadlocal.     */
    private static final ThreadLocal THREADTRANSACTION = new ThreadLocal();
    /** threadlocal.     */
    private static final ThreadLocal THREADINTERCEPTOR = new ThreadLocal();
    /** JNDI name of sessionfactory.     */
    private static final String JNDI_SESSIONFACTORY = "java:hibernate/HibernateFactory";
    /** If running unit tests set to true.     */
    private static boolean offlineMode = false;

    private static SessionFactory sessionFactory;

    /**
     * Go offline when doing unit tests.
     */
    public static void setOfflineMode()
    {
        offlineMode = true;
    }

    /**
     * Returns the SessionFactory used for this static class. If offlineMode has been set
     * then we use hibernate.cfg.xml to create sessionfactory, if not then we use sessionfactory
     * bound to JNDI.
     *
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory()
    {
        if(sessionFactory==null)
        {
            if (offlineMode)
            {
                configuration = new Configuration();
                sessionFactory = configuration.configure().buildSessionFactory();
            }
            else
            {
                try
                {
                    Context ctx = new InitialContext();
                    sessionFactory = (SessionFactory) ctx.lookup(JNDI_SESSIONFACTORY);
                } catch (NamingException ex)
                {
                    throw new InfrastructureException(ex);
                }
            }
        }


        if (sessionFactory == null)
        {
            throw new IllegalStateException("SessionFactory not available.");
        }
        return sessionFactory;
    }


    public static void setSessionFactory(SessionFactory sessionFactory)
    {
        HibernateUtil.sessionFactory = sessionFactory;
    }


    /**
     * Returns the original Hibernate configuration.
     *
     * @return Configuration
     */
    public static Configuration getConfiguration()
    {
        return configuration;
    }


    /**
     * Retrieves the current Session local to the thread.
     * <p/>
     * If no Session is open, opens a new Session for the running thread.
     *
     * @return Session
     */
    public static Session getSession() throws InfrastructureException
    {
        Session s = (Session) THREADSESSION.get();
        try
        {
            if (s == null)
            {
//                log.debug("Opening new Session for this thread.");
                if (getInterceptor() != null)
                {
                    log.debug("Using interceptor: " + getInterceptor().getClass());
                    s = getSessionFactory().openSession(getInterceptor());
                }
                else
                {
                    s = getSessionFactory().openSession();
                }
                THREADSESSION.set(s);
            }
        }
        catch (HibernateException ex)
        {
            throw new InfrastructureException(ex);
        }
        return s;
    }

    /**
     * Closes the Session local to the thread.
     */
    public static void closeSession() throws InfrastructureException
    {
        try
        {
            Session s = (Session) THREADSESSION.get();
            THREADSESSION.set(null);
            if (s != null && s.isOpen())
            {
//                log.debug("Closing Session of this thread.");
                s.close();
            }
        }
        catch (HibernateException ex)
        {
            throw new InfrastructureException(ex);
        }
    }

    /**
     * Start a new database transaction.
     */
    public static void beginTransaction() throws InfrastructureException
    {
        Transaction tx = (Transaction) THREADTRANSACTION.get();
        try
        {
            if (tx == null)
            {
//                log.debug("Starting new database transaction in this thread.");
                tx = getSession().beginTransaction();
                THREADTRANSACTION.set(tx);
            }
        }
        catch (HibernateException ex)
        {
            throw new InfrastructureException(ex);
        }
    }

    /**
     * Commit the database transaction.
     */
    public static void commitTransaction() throws InfrastructureException
    {
        Transaction tx = (Transaction) THREADTRANSACTION.get();
        try
        {
            if (tx != null && !tx.wasCommitted()
                && !tx.wasRolledBack())
            {
//                log.debug("Committing database transaction of this thread.");
                tx.commit();
            }
            THREADTRANSACTION.set(null);
        }
        catch (HibernateException ex)
        {
            rollbackTransaction();
            throw new InfrastructureException(ex);
        }
    }

    /**
     * Commit the database transaction.
     */
    public static void rollbackTransaction() throws InfrastructureException
    {
        Transaction tx = (Transaction) THREADTRANSACTION.get();
        try
        {
            THREADTRANSACTION.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
            {
//                log.debug("Tyring to rollback database transaction of this thread.");
                tx.rollback();
            }
        }
        catch (HibernateException ex)
        {
            throw new InfrastructureException(ex);
        }
        finally
        {
            closeSession();
        }
    }

    /**
     * Reconnects a Hibernate Session to the current Thread.
     *
     * @param session The Hibernate Session to be reconnected.
     */
    public static void reconnect(Session session) throws
        InfrastructureException
    {
        try
        {
            session.reconnect();
            THREADSESSION.set(session);
        }
        catch (HibernateException ex)
        {
            throw new InfrastructureException(ex);
        }
    }

    /**
     * Disconnect and return Session from current Thread.
     *
     * @return Session the disconnected Session
     */
    public static Session disconnectSession() throws InfrastructureException
    {
        Session session = getSession();
        try
        {
            THREADSESSION.set(null);
            if (session.isConnected() && session.isOpen())
            {
                session.disconnect();
            }
        }
        catch (HibernateException ex)
        {
            throw new InfrastructureException(ex);
        }
        return session;
    }

    /**
     * Register a Hibernate interceptor with the current thread.
     *
     * Every Session opened is opened with this interceptor after
     * registration. Has no effect if the current Session of the
     * thread is already open, effective on next close()/getSession().
     *
     * @param interceptor Interceptor
     */
    public static void registerInterceptor(Interceptor interceptor)
    {
        THREADINTERCEPTOR.set(interceptor);
    }

    /**
     * Get Hibernate interceptor.
     * @return Interceptor
     */
    private static Interceptor getInterceptor()
    {
        Interceptor interceptor = (Interceptor) THREADINTERCEPTOR.get();
        return interceptor;
    }

}

