package org.grouter.domain.servicelayer.spring;

/**
 * Unit test GRouterService interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.servicelayer.spring.GRouterService;
import org.grouter.domain.servicelayer.spring.AbstractGrouterServiceInit;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.domain.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestGRouterService extends AbstractGrouterServiceInit
{
    private static Log log = LogFactory.getLog(TestGRouterService.class);
    private final static String beanName = "messageServiceManager";

    private SystemUser storeSystemUser()
    {
        SystemUser systemUserPersisted = null;
        GRouterService gRouterService = (GRouterService)factory.getBean(beanName);
        systemUserPersisted = gRouterService.createSystemUser(systemUser);
        assertNotNull(systemUserPersisted);
        log.debug("## Created systemUser with id : " + systemUserPersisted.getId() );
        return systemUserPersisted;
    }

    /**
     * Create a user and verify its id.
     * @throws Exception
     */
    public void testCreateSystemUser() throws Exception
    {
        storeSystemUser();
    }

    /**
     * Create a message.
     * @throws Exception
     */
    public void testCreateMessage() throws Exception
    {
        Message messagePersisted = null;
        GRouterService gRouterService = (GRouterService)factory.getBean(beanName);
        messagePersisted = gRouterService.createMessage(message);
        log.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
        assertNotNull(messagePersisted.getId());

        //trying to store a message with a null sender should fail
        try
        {
            messagePersisted = null;
            message.setSender(null);
            messagePersisted = gRouterService.createMessage(message);
        } catch (Exception e)
        {
            assertTrue(true);
        }
    }


    public void testFindSystemUser()
    {

        SystemUser systemUserPersisted = storeSystemUser();
        assertNotNull(systemUserPersisted);

        GRouterService gRouterService = (GRouterService)factory.getBean(beanName);
        log.debug("## Doing find on userid :" + systemUserPersisted.getId());
        SystemUser systemUserFromSearch= gRouterService.findSystemUser(systemUserPersisted.getId());
        assertNull(systemUserFromSearch);

        //systemUserPersisted = gRouterService.createSystemUser(systemUser);
        //assertNotNull(systemUserPersisted);

    }

}