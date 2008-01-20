package org.grouter.domain.daolayer.ejb3;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author Georges Polyzois
 */
public abstract class AbstractDAOTests extends AbstractJpaTests
{


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


