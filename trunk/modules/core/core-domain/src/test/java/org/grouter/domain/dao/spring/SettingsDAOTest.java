package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.SettingsDAO;
import org.grouter.domain.entities.Settings;
import org.hibernate.LazyInitializationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class SettingsDAOTest extends AbstractDAOTests
{
    SettingsDAO settingsDAO;
    private static Log log = LogFactory.getLog(SettingsDAOTest.class);


    public void setSettingsDAO(SettingsDAO settingsDAO)
    {
        this.settingsDAO = settingsDAO;
    }

    public void testFindById()
    {
        Settings settings = settingsDAO.findById(SETTINGS_ID);
        assertNotNull(settings.toString());
        assertEquals(SETTINGS_ID, settings.getId());

        //Map map = settings.getSettingsContext();
        //assertEquals( "localhost", map.get( "ftpHost" ) );
        //assertEquals( "12345", map.get( "ftpPort" ) );
    }

    public void testDelete()
    {
        // we are not deleteing settings - overrideing delete in DAOImpl
        // settings
        settingsDAO.delete(SETTINGS_ID);

        flushSession();

        Settings settings = settingsDAO.findById(SETTINGS_ID);
        assertNotNull(settings.toString());
    }


    @Override
    public void testSave()
    {
        Settings settings = new Settings();
        
        Map<String, String> context = new HashMap<String, String>();
        context.put("key", "value");
        context.put("key2", "value2");
        context.put("key2", "value2");   // since we are using a map this does not matter

        settings.setSettingsContext(context);

        settingsDAO.save(settings);
        flushSession();

        assertNotNull(settings.getId());

        Long id = settings.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM settings WHERE id = ?", new Object[]{id});
        assertEquals(id , map.get("id"));

        setComplete();
        
        List list = jdbcTemplate.queryForList("SELECT * FROM settings_context WHERE settings_fk = ?", new Object[]{id});
        assertEquals(2, list.size() );
    }


    @Override
    public void testLazyCollections()
    {
        Settings settings = settingsDAO.findById(SETTINGS_ID);

        assertNotNull(settings);

        // end transaction to simulate a remote request where the session was closed
        endTransaction();

        try
        {
            settings.getSettingsContext().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }
    }

}