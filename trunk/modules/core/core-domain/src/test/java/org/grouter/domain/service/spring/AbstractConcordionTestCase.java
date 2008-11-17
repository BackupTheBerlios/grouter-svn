package org.grouter.domain.service.spring;

import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;

/**
 *
 *
 * @author Georges Polyzois
 */
public abstract class AbstractConcordionTestCase extends AbstractServiceTests
{
    public void testProcessSpecification() throws Throwable {
        ResultSummary resultSummary = new ConcordionBuilder().build().process(this);
        resultSummary.print(System.out);
        resultSummary.assertIsSatisfied();
    }
}
