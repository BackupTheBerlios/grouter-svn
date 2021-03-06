package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.RoleDAO;
import org.grouter.domain.entities.Role;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class RoleDAOTest extends AbstractDAOTests
{
    private static Log log = LogFactory.getLog(RoleDAOTest.class);

    @Override
    public void testFindById()
    {
        Role found = roleDAO.findById(Role.SUPER_REVIEWER.getId());
        assertNotNull(found.toString());
        assertEquals(Role.SUPER_REVIEWER.getId(), found.getId());

        found = roleDAO.findById(Role.EDITOR.getId());
        assertNotNull(found.toString());
        assertEquals(Role.EDITOR.getId(), found.getId());

        found = roleDAO.findById(Role.REVIEWER.getId());
        assertNotNull(found.toString());
        assertEquals(Role.REVIEWER.getId(), found.getId());
    }

    @Override
    public void testSave()
    {
        Role user = null;
        try
        {
            roleDAO.save(user);
            flushSession();
            fail("Role can not be saved");
        } catch (Exception e)
        {
            //expected
        }
    }

    @Override
    public void testLazyCollections()
    {
        // no relations

    }

    @Override
    public void testDelete()
    {
        try
        {
            roleDAO.delete(Role.EDITOR.getId());
            flushSession();

        } catch (Exception e)
        {
            //expected
        }
    }


    public void testRoles()
    {
        int numberOfRoles = roleDAO.findAll().size();
        assertEquals(numberOfRoles, Role.values().size());


    }
}