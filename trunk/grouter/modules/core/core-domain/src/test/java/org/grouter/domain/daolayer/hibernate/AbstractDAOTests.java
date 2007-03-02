package org.grouter.domain.daolayer.hibernate;

import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.hibernate.SessionFactory;
import org.apache.commons.io.FileUtils;

import java.util.List;

/**
 * 
 *
 * @author Georges Polyzois
 */
public abstract class AbstractDAOTests extends AbstractTransactionalDataSourceSpringContextTests
{
    // inserted test data and ids
    String MESSAGE_ID = "msgid_1";


    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        "context-domain-datasource.xml",
                        "context-domain-sessionfactory.xml", "context-domain-dao.xml" 
                };
    }


    protected String getTestDataLocation()
    {
        return "sql/test-domain-data.sql";
    }

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
