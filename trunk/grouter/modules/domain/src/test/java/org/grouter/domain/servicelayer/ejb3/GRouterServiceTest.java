package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.servicelayer.spring.AbstractGrouterServiceInit;
import org.grouter.domain.servicelayer.spring.GRouterService;
import org.grouter.domain.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class GRouterServiceTest extends AbstractGrouterServiceInit {
    private static Log log = LogFactory.getLog(GRouterServiceTest.class);
    private final static String beanName = "messageServiceManager";


    /**
     * Create a message.
     *
     * @throws Exception
     */
    public void testCreateMessage() throws Exception {
        Message messagePersisted = null;
        GRouterService gRouterService = (GRouterService) factory.getBean(beanName);
        messagePersisted = gRouterService.createMessage(message);
        log.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
        assertNotNull(messagePersisted.getId());
    }
}