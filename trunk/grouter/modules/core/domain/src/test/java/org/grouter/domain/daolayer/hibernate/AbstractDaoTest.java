package org.grouter.domain.daolayer.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.apache.commons.io.FileUtils;

import java.util.List;


/**
 * Reads context files used for testing. DAO test classes should extends this class.
 *
 * @author  Georges Polyzois
 */
public abstract class AbstractDaoTest extends AbstractTransactionalDataSourceSpringContextTests
{

    protected abstract String getTestDataLocation();

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected String[] getConfigLocations()
    {
        return new String[]{
                "ac-dao.xml",
                "ac-datasource.xml",
                "ac-service.xml",
                "ac-sessionfactory.xml"

        };
    }

    @Override
    protected void onSetUpInTransaction() throws Exception
    {
        super.onSetUpInTransaction();
        List<String> lines = FileUtils.readLines(new ClassPathResource(getTestDataLocation()).getFile(), "ISO-8859-1");
        for (String line : lines)
        {
            if (StringUtils.hasText(line))
            {
                jdbcTemplate.execute(line);
            }
        }
    }

}
