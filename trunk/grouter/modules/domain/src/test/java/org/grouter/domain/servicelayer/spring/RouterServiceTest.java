package org.grouter.domain.servicelayer.spring;

/**
 * Unit test GRouterServiceImpl interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.servicelayer.AbstractRouterServiceInit;
import org.grouter.domain.servicelayer.GRouterService;
import org.grouter.domain.servicelayer.RouterMessageFactory;
import org.grouter.domain.entities.systemuser.SystemUser;
import org.grouter.domain.entities.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RouterServiceTest extends AbstractRouterServiceInit
{
    private static Log logger = LogFactory.getLog(RouterServiceTest.class);
    private final static String beanName = "messageServiceManager";

    static
    {
        isSpringTest = true;
    }

    public RouterServiceTest()
    {
        super();
    }

    /**
     * @return
     */
    public SystemUser storeSystemUser()
    {
        SystemUser systemUserPersisted = null;
        GRouterService service = (GRouterService) factory.getBean(beanName);
        systemUserPersisted = service.createSystemUser(systemUser);
        assertNotNull(systemUserPersisted);
        logger.debug("## Created systemUser with id : " + systemUserPersisted.getId());
        return systemUserPersisted;
    }


    /**
     * Create a message.
     *
     * @throws Exception
     */
    public void testCreateMessage() throws Exception
    {
        Message messagePersisted = null;
        GRouterService gRouterServiceService = (GRouterService) factory.getBean(beanName);
        messagePersisted = gRouterServiceService.createMessage(message);
        logger.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
        assertNotNull(messagePersisted.getId());
    }

    public void testFindMessageById()
    {
        GRouterService gRouterServiceService = (GRouterService) factory.getBean(beanName);
        Message localmessage = RouterMessageFactory.createRouter();
        Message messagePersisted = gRouterServiceService.createMessage(localmessage);
        logger.debug("## Saved instance with id : " + messagePersisted.getId() + " timestamp " + messagePersisted.getCreationTimestamp());
        assertNotNull(messagePersisted.getId());

        Message foundMessage = gRouterServiceService.findMessageById(messagePersisted.getId());
        assertNotNull(foundMessage.getId());


    }

    /**
     * A null sender is not allowed - should throw an exception.
     * Trying to store a localmessage with a null sender should fail.
     */
    /*public void testCreateMessageWithNullSender() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = null;
        Message localmessage = new Message("A test localmessage never to be persisted");
        Receiver receiver = new Receiver("A test receiver never to be persisted");
        localmessage.addToReceivers(receiver);
        localmessage.setSender(sender);
        logger.debug("##" + timestamp);
        localmessage.setCreationTimestamp(timestamp);
        GRouterService gRouterService = (GRouterService) factory.getBean(beanName);
        try {
            Message messagePersisted = gRouterService.createMessage(localmessage);
        }
        catch (Exception e) {
            assertTrue(true);
        }
        //

    }
    */

    /*public void testFindSystemUser() {

        SystemUser systemUserPersisted = storeSystemUser();
        assertNotNull(systemUserPersisted);

        GRouterServiceImpl gRouterService = (GRouterServiceImpl) factory.getBean(beanName);
        logger.debug("## Doing find on userid :" + systemUserPersisted.getId());
        SystemUser systemUserFromSearch = gRouterService.findSystemUser(systemUserPersisted.getId());
        assertNull(systemUserFromSearch);

        //systemUserPersisted = gRouterService.createSystemUser(systemUser);
        //assertNotNull(systemUserPersisted);

    } */

}