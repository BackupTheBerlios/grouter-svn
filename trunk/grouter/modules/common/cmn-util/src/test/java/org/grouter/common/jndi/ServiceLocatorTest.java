package org.grouter.common.jndi;

import javax.naming.Context;
import javax.ejb.*;
import javax.jms.*;
import javax.sql.DataSource;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.PrintWriter;

/**
 * Mock the Context to the Servicelocator and run some simple lookup tests
 * to see that the cach behaves as should..A second lookup on same jdni name
 * should give us a cache hit.
 *
 * @author Georges Polyzois
 *
 */
public class ServiceLocatorTest extends MockObjectTestCase
{
    private ServiceLocator instanceUnderTest = ServiceLocator.getInstance();
    private static final String JNDINAME_EJBLOCALHOME = "ejbLocalHome";
    private static final String JNDINAME_EJBHOME = "ejbHome";
    private static final String JNDINAME_QUEUE = "queue";
    private static final String JNDINAME_TOPIC = "topic";
    private static final String JNDINAME_DATASOURCE = "datasource";
    private static final String JNDINAME_TOPICFACTORY = "topicfactory";
    private static final String JNDINAME_QUEUEFACTORY = "queuefactory";

    protected Mock mockContext;

    /**
     * Setup.
     * @throws Exception
     */
    public void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * Tear down.
     * @throws Exception
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test cache size increases as expected.
     */
    public void testCacheSizeIncreaseOnLookup()
    {
        //setup
        mockContext = mock(Context.class);
        instanceUnderTest.setInitialContext((Context)mockContext.proxy());

        //expectations - if a lookup is called more than once then we are in trouble
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_EJBLOCALHOME)).will(returnValue(new EJBLocalMock()));
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_EJBHOME)).will(returnValue(new EJBHomeMock()));
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_QUEUE)).will(returnValue(new QueueMock()));
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_TOPIC)).will(returnValue(new TopicMock()));
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_DATASOURCE)).will(returnValue(new DatasourceMock()));
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_QUEUEFACTORY)).will(returnValue(new QueueConnectionFactoryMock()));
        mockContext.expects(once()).method("lookup").with(eq(JNDINAME_TOPICFACTORY)).will(returnValue(new TopicConnectionFactoryMock()));


        //execute
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME);
        assertEquals(1,instanceUnderTest.getCacheSize());
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME);  //should produce a cache hit!!
        assertEquals(1,instanceUnderTest.getCacheSize());

        instanceUnderTest.getHome(JNDINAME_EJBHOME);
        assertEquals(2,instanceUnderTest.getCacheSize());
        instanceUnderTest.getHome(JNDINAME_EJBHOME); //should produce a cache hit!!
        assertEquals(2,instanceUnderTest.getCacheSize());

        instanceUnderTest.getQueue(JNDINAME_QUEUE);
        assertEquals(3,instanceUnderTest.getCacheSize());
        instanceUnderTest.getQueue(JNDINAME_QUEUE);
        assertEquals(3,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getTopic(JNDINAME_TOPIC);
        assertEquals(4,instanceUnderTest.getCacheSize());
        instanceUnderTest.getTopic(JNDINAME_TOPIC);
        assertEquals(4,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getDataSource(JNDINAME_DATASOURCE);
        assertEquals(5,instanceUnderTest.getCacheSize());
        instanceUnderTest.getDataSource(JNDINAME_DATASOURCE);
        assertEquals(5,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getTopicConnectionFactory(JNDINAME_TOPICFACTORY);
        assertEquals(6,instanceUnderTest.getCacheSize());
        instanceUnderTest.getTopicConnectionFactory(JNDINAME_TOPICFACTORY);
        assertEquals(6,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getQueueConnectionFactory(JNDINAME_QUEUEFACTORY);
        assertEquals(7,instanceUnderTest.getCacheSize());
        instanceUnderTest.getQueueConnectionFactory(JNDINAME_QUEUEFACTORY);
        assertEquals(7,instanceUnderTest.getCacheSize());  //should produce a cache hit!!
    }


    /**
     * Used to receive some return value from mock.
     */
    class EJBLocalMock implements EJBLocalHome
    {
        public void remove(Object object) throws RemoveException, EJBException
        {
        }
    }

    /**
     * Used to receive some return value from mock.
     */
    class EJBHomeMock implements EJBHome
    {
        public EJBMetaData getEJBMetaData() throws RemoteException
        {
            return null;
        }

        public HomeHandle getHomeHandle() throws RemoteException
        {
            return null;
        }

        public void remove(Handle handle) throws RemoteException, RemoveException
        {
        }

        public void remove(Object object) throws RemoteException, RemoveException
        {
        }
    }

    /**
     * Used to receive some return value from mock.
     */
    class QueueMock implements Queue
    {
        public String getQueueName() throws JMSException
        {
            return JNDINAME_QUEUE;
        }
    }


    /**
     * Used to receive some return value from mock.
     */
    class TopicMock implements Topic
    {
        public String getTopicName() throws JMSException
        {
            return JNDINAME_TOPIC;
        }
    }
    /**
     * Used to receive some return value from mock.
     */
    class DatasourceMock implements DataSource
    {

        public Connection getConnection() throws SQLException
        {
            return null;
        }

        public Connection getConnection(String username, String password) throws SQLException
        {
            return null;
        }

        public PrintWriter getLogWriter() throws SQLException
        {
            return null;
        }

        public void setLogWriter(PrintWriter out) throws SQLException
        {
        }

        public void setLoginTimeout(int seconds) throws SQLException
        {
        }

        public int getLoginTimeout() throws SQLException
        {
            return 0;
        }
    }

    /**
     * Used to receive some return value from mock.
     */
    class QueueConnectionFactoryMock implements QueueConnectionFactory
    {
        public QueueConnection createQueueConnection() throws JMSException
        {
            return null;
        }

        public QueueConnection createQueueConnection(String string, String string1) throws JMSException
        {
            return null;
        }

        public javax.jms.Connection createConnection() throws JMSException
        {
            return null;
        }

        public javax.jms.Connection createConnection(String string, String string1) throws JMSException
        {
            return null;
        }
    }

    /**
     * Used to receive some return value from mock.
     */
    class TopicConnectionFactoryMock implements TopicConnectionFactory
    {

        public TopicConnection createTopicConnection() throws JMSException
        {
            return null;
        }

        public TopicConnection createTopicConnection(String string, String string1) throws JMSException
        {
            return null;
        }

        public javax.jms.Connection createConnection() throws JMSException
        {
            return null;
        }

        public javax.jms.Connection createConnection(String string, String string1) throws JMSException
        {
            return null;
        }
    }

}
