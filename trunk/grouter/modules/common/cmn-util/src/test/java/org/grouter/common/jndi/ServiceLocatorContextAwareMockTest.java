package org.grouter.common.jndi;

import org.jmock.MockObjectTestCase;
import org.jmock.Mock;

import javax.naming.Context;
import javax.ejb.*;
import javax.jms.*;
import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * Mock the Context to the Servicelocator and run some simple lookup tests
 * to see that the cach behaves.
 *
 * @author Georges Polyzois
 *
 */
public class ServiceLocatorContextAwareMockTest extends MockObjectTestCase
{
    private ServiceLocatorContextAware instanceUnderTest = ServiceLocatorContextAware.getInstance();
    private static final String JNDINAME_EJBLOCALHOME = "ejbLocalHome";
    private static final String JNDINAME_EJBHOME = "ejbHome";
    private static final String JNDINAME_QUEUE = "queue";
    private static final String JNDINAME_TOPIC = "topic";
    private static final String JNDINAME_DATASOURCE = "datasource";
    private static final String JNDINAME_TOPICFACTORY = "topicfactory";
    private static final String JNDINAME_QUEUEFACTORY = "queuefactory";
    protected Mock mockContext1;
    protected Mock mockContext2;

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
        //setup - could cal   l mock instead...
        mockContext1 = mock(Context.class);

        //expectations
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_EJBLOCALHOME)).will(returnValue(new EJBLocalMock()));
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.PROVIDER_URL,"MOCKPROVIDERURL");
        mockContext1.expects(atLeastOnce()).method("getEnvironment").will(returnValue(hashtable));


        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_EJBHOME)).will(returnValue(new EJBHomeMock()));
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_QUEUE)).will(returnValue(new QueueMock()));
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_TOPIC)).will(returnValue(new TopicMock()));
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_DATASOURCE)).will(returnValue(new DatasourceMock()));
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_QUEUEFACTORY)).will(returnValue(new QueueConnectionFactoryMock()));
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_TOPICFACTORY)).will(returnValue(new TopicConnectionFactoryMock()));

        //execute
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME,(Context)mockContext1.proxy());
        assertEquals(1,instanceUnderTest.getCacheSize());
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME,(Context)mockContext1.proxy());  //should produce a cache hit!!
        assertEquals(1,instanceUnderTest.getCacheSize());

        instanceUnderTest.getHome(JNDINAME_EJBHOME,(Context)mockContext1.proxy());
        assertEquals(2,instanceUnderTest.getCacheSize());
        instanceUnderTest.getHome(JNDINAME_EJBHOME,(Context)mockContext1.proxy()); //should produce a cache hit!!
        assertEquals(2,instanceUnderTest.getCacheSize());

        instanceUnderTest.getQueue(JNDINAME_QUEUE,(Context)mockContext1.proxy());
        assertEquals(3,instanceUnderTest.getCacheSize());
        instanceUnderTest.getQueue(JNDINAME_QUEUE,(Context)mockContext1.proxy());
        assertEquals(3,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getTopic(JNDINAME_TOPIC,(Context)mockContext1.proxy());
        assertEquals(4,instanceUnderTest.getCacheSize());
        instanceUnderTest.getTopic(JNDINAME_TOPIC,(Context)mockContext1.proxy());
        assertEquals(4,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getDataSource(JNDINAME_DATASOURCE,(Context)mockContext1.proxy());
        assertEquals(5,instanceUnderTest.getCacheSize());
        instanceUnderTest.getDataSource(JNDINAME_DATASOURCE,(Context)mockContext1.proxy());
        assertEquals(5,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getTopicConnectionFactory(JNDINAME_TOPICFACTORY,(Context)mockContext1.proxy());
        assertEquals(6,instanceUnderTest.getCacheSize());
        instanceUnderTest.getTopicConnectionFactory(JNDINAME_TOPICFACTORY,(Context)mockContext1.proxy());
        assertEquals(6,instanceUnderTest.getCacheSize());  //should produce a cache hit!!

        instanceUnderTest.getQueueConnectionFactory(JNDINAME_QUEUEFACTORY,(Context)mockContext1.proxy());
        assertEquals(7,instanceUnderTest.getCacheSize());
        instanceUnderTest.getQueueConnectionFactory(JNDINAME_QUEUEFACTORY,(Context)mockContext1.proxy());
        assertEquals(7,instanceUnderTest.getCacheSize());  //should produce a cache hit!!
    }

    /**
     * Test cache size increases as expected.
     */
    public void testNullInitialContext()
    {
        try
        {
            instanceUnderTest.getQueue(JNDINAME_QUEUE,null);
        }
        catch(Exception e)
        {
            assertTrue("Should always throw an exception on null context",true);
        }
    }

    /**
     * Test cache size increases as expected.
     */
    public void testCacheSizeIncreaseOnLookupWithDifferentContexts()
    {
        //setup - could cal   l mock instead...
        mockContext1 = mock(Context.class);
        mockContext2 = mock(Context.class);

        //expectations
        mockContext1.expects(once()).method("lookup").with(eq(JNDINAME_EJBLOCALHOME)).will(returnValue(new EJBLocalMock()));
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.PROVIDER_URL,"MOCKPROVIDERURL1");
        mockContext1.expects(atLeastOnce()).method("getEnvironment").will(returnValue(hashtable));

        mockContext2.expects(once()).method("lookup").with(eq(JNDINAME_EJBLOCALHOME)).will(returnValue(new EJBLocalMock()));
        Hashtable hashtable2 = new Hashtable();
        hashtable.put(Context.PROVIDER_URL,"MOCKPROVIDERURL2");
        mockContext2.expects(atLeastOnce()).method("getEnvironment").will(returnValue(hashtable2));

        //execute
        int cachesize = instanceUnderTest.getCacheSize();
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME,(Context)mockContext1.proxy());
        assertEquals(cachesize+1,instanceUnderTest.getCacheSize());
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME,(Context)mockContext1.proxy());  //should produce a cache hit!!
        assertEquals(cachesize+1,instanceUnderTest.getCacheSize());

        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME,(Context)mockContext2.proxy());
        assertEquals(cachesize+2,instanceUnderTest.getCacheSize());
        instanceUnderTest.getLocalHome(JNDINAME_EJBLOCALHOME,(Context)mockContext2.proxy());  //should produce a cache hit!!
        assertEquals(cachesize+2,instanceUnderTest.getCacheSize());

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


        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
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
