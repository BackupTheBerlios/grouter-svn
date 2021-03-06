package org.grouter.domain.service.spring;


import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Loads context files for Spring tests.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractServiceTests extends AbstractTransactionalDataSourceSpringContextTests
{
    SessionFactory sessionFactory;
    Long MESSAGE_ID = -1L;
    String NODE_ID = "nodeid_1";


    protected AbstractServiceTests()
    {
        setAutowireMode(AUTOWIRE_BY_NAME);
    }

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
     * Specify all context files here.
     *
     * @return an array with context files
     */
    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        // "context-domain-aop.xml","context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-datasource.xml", "context-domain-dao.xml",
                        "context-domain-sessionfactory.xml", "context-domain-service.xml"
                };
    }


    /**
     * The data we want to use in our test cases.
     *
     * @return a path to test data script
     */
    protected String getTestDataLocation()
    {
        return "/test-domain-data.sql";
    }

    /**
     * Convinience method.
     */
    protected void flushSession()
    {
        sessionFactory.getCurrentSession().flush();
    }


    @SuppressWarnings("unchecked")
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
}

