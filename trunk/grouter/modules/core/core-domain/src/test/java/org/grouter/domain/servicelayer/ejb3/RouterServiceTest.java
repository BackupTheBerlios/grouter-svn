package org.grouter.domain.servicelayer.ejb3;

import org.grouter.domain.entities.Message;
import org.grouter.domain.servicelayer.AbstractRouterServiceInit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RouterServiceTest extends AbstractRouterServiceInit
{
    private static Log log = LogFactory.getLog(RouterServiceTest.class);

    static
    {
        isSpringTest = false;
    }

    public RouterServiceTest()
    {
        super();
    }

    /**
     * Create a message.
     *
     * @throws Exception
     */
    public void testCreateMessage() throws Exception
    {
        Message messagePersisted = remoteRouter.createMessage(message);
        log.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
        assertNotNull(messagePersisted.getId());
    }



}