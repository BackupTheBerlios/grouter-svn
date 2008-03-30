package org.grouter.domain.dao.spring;

import org.apache.commons.lang.Validate;
import org.grouter.domain.dao.UserDAO;
import org.grouter.domain.entities.*;
import org.hibernate.LazyInitializationException;
import org.hibernate.validator.InvalidValue;

import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class UserDAOTest extends AbstractDAOTests
{
    UserDAO userDAO;


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
        assertEquals("rid", found.getPassword());
        endTransaction();
        assertEquals( "astrid.lindgren@stockholm.se", found.getAddress().getEmail() );
    }

    @Override
    public void testSave()
    {
        Address address = new Address();
        address.setEmail("email");
                
        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setCreatedOn( new Date() );
        auditInfo.setModifiedOn( new Date() );
        auditInfo.setCreatedBy(User.SYSTEM);

        User user = new User();
        user.setAuditInfo(auditInfo);
        user.setAddress( address );
        user.setFirstName("A first name");
        user.setLastName("A last name");
        user.setPassword("password");
        user.setUserName("username");
        user.setUserState(UserState.BLOCKED);

        UserRole userRole = new UserRole( user, Role.SUPER_REVIEWER );
        UserRole userRole2 = new UserRole( user, Role.ADMIN );
        UserRole userRole3 = new UserRole( user, Role.SUPER_REVIEWER );
        user.getUserRoles().add( userRole  );
        user.getUserRoles().add( userRole2  );
        user.getUserRoles().add( userRole3  );

        userDAO.save(user);
        flushSession();
        Long id = user.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM user WHERE id = ?", new Object[]{id});
        assertEquals("A first name", map.get("firstname"));

        int size = jdbcTemplate.queryForInt("SELECT count(*) FROM user_role WHERE user_id =" + id );
        assertEquals(3, size);


        map = jdbcTemplate.queryForMap("SELECT createdby, modifiedby FROM user WHERE id =" + id );
        assertEquals(User.SYSTEM.getId(), map.get("createdby"));

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
            user.getUserRoles().toString();
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
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM address where id=-1"));
        userDAO.delete(USER_ID);
        // Should cascade a delete to the Address entity
        flushSession();
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM user WHERE id =" + USER_ID));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM user_role WHERE user_id =" + USER_ID));
        assertEquals(4, jdbcTemplate.queryForInt("SELECT count(*) FROM role"));
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM address where id=-1"));
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
        assertEquals(TOTALNUMBEROFUSERS, user.size());
    }

    public void testFindAllWithHQL()
    {
        List<User> user = userDAO.findAll(UserDAO.FIND_ALL);
        assertEquals(TOTALNUMBEROFUSERS, user.size());
    }
}