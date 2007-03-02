package org.grouter.domain.daolayer.ejb3;

import org.apache.commons.io.FileUtils;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * @author Georges Polyzois
 */
public abstract class AbstractDAOTests extends AbstractJpaTests
{
    SessionFactory sessionFactory;
    EntityManager entityManager;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
                {
                        "context-domain-dao.xml", "context-domain-datasource.xml",
                        "context-domain-sessionfactory.xml"
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


    public void setEntityManager(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }
}


