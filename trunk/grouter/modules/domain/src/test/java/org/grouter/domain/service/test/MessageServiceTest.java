package org.grouter.domain.service.test;

/**
 * Unit test MessageService interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.common.logging.Log4JInit;
import org.grouter.domain.servicelayer.pojoservice.MessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import junit.framework.TestCase;

public class MessageServiceTest extends SpringTest
{
    private static Log log = LogFactory.getLog(MessageServiceTest.class);
    private final static String beanName = "messageService";

    /**
     *
     * @throws Exception
     */
    public void doSomething() throws Exception
    {
        MessageService ref = (MessageService)factory.getBean(beanName);
        ref.createSystemUser(null);

    }
}