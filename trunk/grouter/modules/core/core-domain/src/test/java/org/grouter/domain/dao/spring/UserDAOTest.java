package org.grouter.domain.dao.spring;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.UserDAO;
import org.grouter.domain.entities.EntityValidator;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.UserState;
import org.hibernate.LazyInitializationException;
import org.hibernate.validator.InvalidValue;

import java.util.List;
import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class UserDAOTest extends AbstractDAOTests
{
    UserDAO userDAO;
    private static Log log = LogFactory.getLog(UserDAOTest.class);


    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    @Override
    public void testFindById()
    {
        User found = userDAO.findById(USER_ID);
        assertNotNull(found.toString());
        assertEquals(USER_ID, found.getId());
        assertEquals("gepo", found.getPassword());
    }

    @Override
    public void testSave()
    {

        User admin = userDAO.findById(USER_ID);
        //User admin = new User();
        //admin.setId( ADMIN_ID );

        User user = new User();
        user.setFirstName("A first name");
        user.setLastName("A last name");
        user.setPassword("password");
        user.setUserName("username");
        user.setCreatedBy(admin);
        user.setUserState(UserState.BLOCKED);
        userDAO.save(user);
        flushSession();
        Long id = user.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM user WHERE id = ?", new Object[]{id});
        assertEquals("A first name", map.get("firstname"));
    }

    public void testUserEntityValidator()
    {
        User user = new User();
        // username, firsname,password and userstate can not be null
        InvalidValue[] invalidValues = EntityValidator.validate(user);
        assertNotNull(invalidValues);
        try
        {
            Validate.notEmpty(invalidValues);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    @Override
    public void testLazyCollections()
    {
        User user = userDAO.findById(USER_ID);
        assertNotNull(user);

        endTransaction();
        try
        {
            user.getRoles().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }

        try
        {
            user.getAddress().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }
    }

    @Override
    public void testDelete()
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) from user WHERE id =" + USER_ID));
        assertEquals(3, jdbcTemplate.queryForInt("SELECT count(*) FROM user_role WHERE user_id =" + USER_ID));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT count(*) FROM role"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM address where id=1"));
        userDAO.delete(USER_ID);
        flushSession();
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM user WHERE id =" + USER_ID));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM user_role WHERE user_id =" + USER_ID));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT count(*) FROM role"));
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM address where id=1"));
    }

    public void testSetUserStateToInactive()
    {
        User found = userDAO.findById(USER_ID);
        assertNotNull(found);
        found.setUserState(UserState.BLOCKED);

        flushSession();

        User foundBlocked = userDAO.findById(USER_ID);
        assertNotNull(foundBlocked);
        assertEquals(UserState.BLOCKED, foundBlocked.getUserState());
    }


    public void testFindAll()
    {
        List<User> user = userDAO.findAll();
        assertEquals(3, user.size());
    }

    public void testFindAll2()
    {
        List<User> user = userDAO.findAll(UserDAO.FIND_ALL);
        assertEquals(3, user.size());
    }

}