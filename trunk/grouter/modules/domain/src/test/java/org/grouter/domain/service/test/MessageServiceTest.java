package org.grouter.domain.service.test;

/**
 * Unit test GRouterService interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.servicelayer.spring.GRouterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageServiceTest extends SpringTest
{
    private static Log log = LogFactory.getLog(MessageServiceTest.class);
    private final static String beanName = "messageServiceManager";

    /**
     *
     * @throws Exception
     */
    public void doSomething() throws Exception
    {
        GRouterService ref = (GRouterService)factory.getBean(beanName);
        ref.createSystemUser(systemUser);
    }
}