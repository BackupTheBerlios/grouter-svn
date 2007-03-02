package org.grouter.domain.daolayer.hibernate;

/**
 * Unit test MessageDAO interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.entities.SystemUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SystemDAOInMemoryTest extends AbstractInMemoryDataInitializationTests
{
    private static Log log = LogFactory.getLog(SystemDAOInMemoryTest.class);

    /**
     * Checks if the loaded user is found.
     * @throws Exception
     */
    public void finder() throws Exception
    {
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        SystemUser systemUser = systemUserDAO.findById(super.systemUser1.getId());
        assertNotNull("One user with id : " + super.systemUser1.getId()  ,systemUser);
    }

    /**
     * Checks if the loaded user is found modifies user store and find again - a stupid
     * unit test...
     *
     * @throws Exception
     */
    public void update() throws Exception
    {
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        SystemUser systemUser = systemUserDAO.findById(super.systemUser1.getId());
        assertNotNull("One user with id : " + super.systemUser1.getId()  ,systemUser);
        String expected = "Sir Donald";
        systemUser.setName("Sir Donald");
        systemUserDAO.save(systemUser);
        SystemUser systemUserAltered = systemUserDAO.findById(super.systemUser1.getId());
        assertEquals(expected,systemUserAltered.getName());
    }

}