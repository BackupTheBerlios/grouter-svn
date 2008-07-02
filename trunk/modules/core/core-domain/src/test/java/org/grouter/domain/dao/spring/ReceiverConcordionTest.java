package org.grouter.domain.dao.spring;

import org.concordion.integration.junit3.ConcordionTestCase;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class ReceiverConcordionTest extends ConcordionTestCase
{
    public String greetingFor(String firstName)
    {
        return "Hello " + firstName + "!";

    }

}