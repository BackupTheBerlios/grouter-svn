package org.grouter.domain.servicelayer.spring;

/**
 * Unit test GRouterService interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.servicelayer.spring.GRouterService;
import org.grouter.domain.servicelayer.spring.TestGrouterServiceInit;
import org.grouter.domain.systemuser.SystemUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestSystemUserService extends TestGrouterServiceInit
{
    private static Log log = LogFactory.getLog(TestSystemUserService.class);
    private final static String beanName = "messageServiceManager";

    /**
     * Create a user and verify its id.
     * @throws Exception
     */
    public void testCreateSystemUser() throws Exception
    {
        SystemUser systemUserPersisted = null;
        GRouterService gRouterService = (GRouterService)factory.getBean(beanName);
        systemUserPersisted = gRouterService.createSystemUser(systemUser);
        assertNotNull(systemUserPersisted);
        log.debug("##Created systemUser with id : " + systemUserPersisted.getId() );
    }

    public void testFindById()
    {
        GRouterService gRouterService = (GRouterService)factory.getBean(beanName);
        SystemUser systemUserPersisted = gRouterService.findSystemUser(new Long(-123));
        assertNull(systemUserPersisted);

        //systemUserPersisted = gRouterService.createSystemUser(systemUser);
        //assertNotNull(systemUserPersisted);

    }

}