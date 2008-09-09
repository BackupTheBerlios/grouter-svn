package org.grouter.domain.dao.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.domain.dao.JobDAO;
import org.grouter.domain.dao.RouterDAO;
import org.grouter.domain.entities.Job;
import org.grouter.domain.entities.JobState;
import org.grouter.domain.entities.JobType;
import org.grouter.domain.entities.Router;
import org.hibernate.LazyInitializationException;

import java.util.Map;
import java.util.List;

/**
 * DAO tests for mappings, cascade saves etc.
 *
 * @author Georges Polyzois
 */
public class JobDAOTest extends AbstractDAOTests
{                                                            
    private static Log log = LogFactory.getLog(JobDAOTest.class);
    private static final String ID_JOB_WITH_NO_ROUTER = "-3";
    private static final int NUMEROFJOBS = 3;

    @Override
    public void testFindById()
    {
        Job found = jobDAO.findById(JOB_ID);
        assertNotNull(found.toString());
        assertEquals(JOB_ID, found.getId());
        assertEquals("Job 1", found.getDisplayName());
    }

    @Override
    public void testSave()
    {
        Router router = routerDAO.findById(ROUTER_ID);

        Job job = new Job( "-id123", "displayname", "cronexpr", JobState.RUNNING, JobType.SYNCHRONOUS, router);
        job.setDisplayName("A displayname");
        job.setIdNo("no");
        jobDAO.save(job);
        flushSession();
        String id = job.getId();
        Map map = jdbcTemplate.queryForMap("SELECT * FROM job WHERE id = ?", new Object[]{id});
        assertEquals("A displayname", map.get("displayname"));
    }

    @Override
    public void testLazyCollections()
    {
        Job job = jobDAO.findById(JOB_ID);
        assertNotNull(job);

        endTransaction();
        try
        {
            job.getJobContext().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }

        try
        {
            job.getJobState().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }
        try
        {
            job.getJobType().toString();
        }
        catch (LazyInitializationException lie)
        {
            fail("collection should not be lazy - using fetch join in mapping");
        }
    }

    @Override
    public void testDelete()
    {
        assertEquals(1, jdbcTemplate.queryForInt("SELECT count(*) FROM job WHERE id =" + ID_JOB_WITH_NO_ROUTER));
        jobDAO.delete(ID_JOB_WITH_NO_ROUTER);
        flushSession();
        assertEquals(0, jdbcTemplate.queryForInt("SELECT count(*) FROM job WHERE id =" + ID_JOB_WITH_NO_ROUTER));
    }



    public void testFindAll()
    {
        List<Job> job = jobDAO.findAll();
        assertEquals(NUMEROFJOBS, job.size());
    }


}