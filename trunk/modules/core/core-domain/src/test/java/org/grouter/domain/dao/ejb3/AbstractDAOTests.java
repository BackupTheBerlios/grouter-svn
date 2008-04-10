package org.grouter.domain.dao.ejb3;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * Base class for testing EJB3 enitites
 *
 * @author Georges Polyzois
 */
public abstract class AbstractDAOTests extends AbstractJpaTests
{
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


    protected String getTestDataLocation()
    {
        return "/test-domain-data.sql";
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


