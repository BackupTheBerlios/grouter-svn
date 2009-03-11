package org.grouter.domain.dao;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.grouter.domain.dao.spring.JobDAOTest;

/**
 *
 *
 * @author Georges Polyzois
 */
public class DAOTestSuite extends TestSuite
{
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(JobDAOTest.class);
        return suite;
      }

}
