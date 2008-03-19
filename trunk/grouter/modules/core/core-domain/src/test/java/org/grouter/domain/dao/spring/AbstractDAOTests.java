package org.grouter.domain.dao.spring;

import org.apache.commons.io.FileUtils;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.dao.NodeDAO;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Test the DAO using an external DB which is populated with data before the actual test is run
 * {@link #getTestDataLocation()} and then restored to a state as per before the tests were ran.
 * All plumbing for rolling back the state is taken care of by Springs
 * {@link org.springframework.test.AbstractTransactionalDataSourceSpringContextTests}
 * <p/>
 * AbstractDAOTests referes to the sql to be run before any test are created and run.
 * <p/>
 * We have some abstract methods in this abstract class to indicate to subclasses what they
 * need to test.
 * <p/>
 * The only problem with these types of test are that as we go along and add more and more test
 * the tests start taking considerably long time to execute.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractDAOTests extends AbstractTransactionalDataSourceSpringContextTests
{
    // inserted test data and ids of some of the data
    final static String MESSAGE_ID = "-msgid_1";
    final static String NODE_ID = "-nid_1";
    final static String NODE_ID_FTP = "nid_2";
    final static String ROUTER_ID = "rid_1";
    final static String ENDPOINT_ID = "1";
    final static String SETTINGS_ID = "jndi";
    final static Long JOB_ID = 1L;
    Long USER_ID = -1L;
    Long ADMIN_ID = 1L;
    final static int TOTALNUMBEROFUSERS = 6;

    SessionFactory sessionFactory;
    MessageDAO messageDAO;
    NodeDAO nodeDAO;

    /**
     * Injected.
     *
     * @param sessionFactory injected
     */
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Our context files for these tests.
     *
     * @return context files
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        "context-domain-datasource.xml",
                        "context-domain-sessionfactory.xml",
                        "context-domain-dao.xml",
                        "context-domain-service.xml"
                };
    }


    /**
     * Location of test data realtive to the resources location.
     *
     * @return relative path to test data location
     */
    protected String getTestDataLocation()
    {
        return "/test-domain-data.sql";
    }

    protected void flushSession()
    {
        sessionFactory.getCurrentSession().flush();
    }


    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        super.onSetUpInTransaction();

        List<String> lines = FileUtils.readLines(new ClassPathResource(getTestDataLocation()).getFile(),
                "ISO-8859-1");

        for (String line : lines)
        {
            if (StringUtils.hasText(line))
            {
                jdbcTemplate.execute(line);
            }
        }
    }


    /**
     * Subclasses need to override this and implement a test whereby the relationships are tested. Is a relationship
     * to be loaded lazy or not?
     */
    abstract void testLazyCollections();

    /**
     * Subclasses need to override this and implement a test whereby they load an entity and verify that
     * some of the loaded attributes ot hhe entity are valid.
     */
    abstract void testFindById();

    /**
     * Subclasses need to override this and implement a test whereby they delete an entity and verify that the
     * entity was deleted (or in some cases marked as deleted) and that any cascade options are valid.
     */
    abstract void testDelete();

    /**
     * Subclasses need to override this and implement a test whereby they save an entity and verify that
     * the entity was actually saved.
     */
    abstract void testSave();

    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }
}
