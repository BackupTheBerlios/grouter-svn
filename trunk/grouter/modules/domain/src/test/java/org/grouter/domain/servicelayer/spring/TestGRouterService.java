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
import org.grouter.domain.Sender;
import org.grouter.domain.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;

public class TestGRouterService extends AbstractGrouterServiceInit {
    private static Log log = LogFactory.getLog(TestGRouterService.class);
    private final static String beanName = "messageServiceManager";

    /**
     * @return
     */
    private SystemUser storeSystemUser() {
        SystemUser systemUserPersisted = null;
        GRouterService gRouterService = (GRouterService) factory.getBean(beanName);
        systemUserPersisted = gRouterService.createSystemUser(systemUser);
        assertNotNull(systemUserPersisted);
        log.debug("## Created systemUser with id : " + systemUserPersisted.getId());
        return systemUserPersisted;
    }

    /**
     * Create a user and verify its id.
     *
     * @throws Exception
     */
    public void testCreateSystemUser() throws Exception {
        storeSystemUser();
    }

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

    /**
     * A null sender is not allowed - should throw an exception.
     * Trying to store a localmessage with a null sender should fail.
     */
    public void testCreateMessageWithNullSender() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = null;
        Message localmessage = new Message("A test localmessage never to be persisted");
        Receiver receiver = new Receiver("A test receiver never to be persisted");
        localmessage.addToReceivers(receiver);
        localmessage.setSender(sender);
        log.debug("##" + timestamp);
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


    public void testFindSystemUser() {

        SystemUser systemUserPersisted = storeSystemUser();
        assertNotNull(systemUserPersisted);

        GRouterService gRouterService = (GRouterService) factory.getBean(beanName);
        log.debug("## Doing find on userid :" + systemUserPersisted.getId());
        SystemUser systemUserFromSearch = gRouterService.findSystemUser(systemUserPersisted.getId());
        assertNull(systemUserFromSearch);

        //systemUserPersisted = gRouterService.createSystemUser(systemUser);
        //assertNotNull(systemUserPersisted);

    }

}