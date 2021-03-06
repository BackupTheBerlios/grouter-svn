package org.grouter.domain.dao.spring;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.common.hibernate.HibernateUtilContextAware;
import org.grouter.common.logging.Log4JInit;
import org.grouter.domain.dao.DAOFactory;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Receiver;
import org.grouter.domain.entities.Sender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.ThreadLocalSessionContext;

/**
 * Unit test configuration is coded in here.
 */
public abstract class AbstractInMemoryHibernateInitializationTests extends TestCase
{
    Log4JInit log4j = new Log4JInit();
    private static Log log = LogFactory.getLog(AbstractInMemoryHibernateInitializationTests.class);

    protected final DAOFactory DAOFACTORY = DAOFactory.HIBERNATE;
    protected static SessionFactory sessionFactory;

    protected boolean wrapInTransaction = true;
    protected boolean rollback = false;

    static
    {
        Configuration config = new Configuration().
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:domaintest").
                //setProperty("hibernate.connection.url", "jdbc:hsqldb:hsql://localhost:9001/grouter_alias").
                        setProperty("hibernate.connection.username", "sa").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.connection.pool_size", "1").
                setProperty("hibernate.connection.autocommit", "true").
                setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
                //setProperty("hibernate.hbm2ddl.auto", "create").  //create-drop
                        setProperty("hibernate.show_sql", "true").
                setProperty("hibernate.current_session_context_class", "thread").
                setProperty("hibernate.jdbc.batch_size", "0").
                addClass(Message.class).
                addClass(Receiver.class).
                addClass(Node.class).
                addClass(Sender.class);

        // TODO HibernateUtilContextAware.buildSessionFactory(config);

        HibernateUtilContextAware.rebuildSessionFactory();
        sessionFactory = HibernateUtilContextAware.getSessionFactory();
    }


    protected void runTest() throws Throwable
    {
        Session session = null;
        if (wrapInTransaction)
        {
            log.debug("Wrapping test in a transaction");
            try
            {
                log.debug("Begin transaction");
                session = sessionFactory.getCurrentSession();
                session.beginTransaction();
                log.debug("Executing inTransaction() supplement");
                inTransaction();
                log.debug("Running test");
                super.runTest();
            }
            catch (Throwable thr)
            {
                log.error(thr, thr);
                throw thr;
            }
            finally
            {
                boolean isSessionOrTransactionNull = (session == null || session.getTransaction() == null);
                if (isSessionOrTransactionNull)
                {
                    log.error("SessionFactory not properly initialised - no session retrieved!");
                }
                if (!rollback)
                {
                    log.debug("Committing transaction");
                    session.getTransaction().commit();
                } else
                {
                    log.debug("Rolling back transaction");
                    session.getTransaction().rollback();
                }
            }
        } else
        {
            super.runTest();
        }
    }

    // Use thread-binding for Long Session
    protected Session disconnectContext()
    {
        log.debug("Disconnecting session from thread");
        return ThreadLocalSessionContext.unbind(sessionFactory);
    }

    protected void reconnectContext(Session s)
    {
        log.debug("Reconnecting session to thread");
        ThreadLocalSessionContext.bind(s);
    }

    /**
     * Executes inside the transaction.
     * <p/>
     * Override this method to execute extra operations, such as test data import.
     */
    public void inTransaction()
    {
    }

}
