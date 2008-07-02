package org.grouter.domain.service.spring;

import org.concordion.internal.ConcordionBuilder;
import org.concordion.api.ResultSummary;
import org.grouter.domain.dao.spring.AbstractDAOTests;

/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Jun 7, 2008
 * Time: 8:54:38 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractConcordionTestCase extends AbstractServiceTests
{
    public void testProcessSpecification() throws Throwable {
        ResultSummary resultSummary = new ConcordionBuilder().build().process(this);
        resultSummary.print(System.out);
        resultSummary.assertIsSatisfied();
    }
}
