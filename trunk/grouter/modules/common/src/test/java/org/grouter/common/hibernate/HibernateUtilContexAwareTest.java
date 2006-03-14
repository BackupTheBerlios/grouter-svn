package org.grouter.common.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-mar-14
 * Time: 08:01:14
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtilContexAwareTest extends TestCase
{

    public void testHibernateBootstrap()
    {
        SessionFactory  sessionFactory = HibernateUtilContextAware.getDefaultSessionFactory();
        assertNotNull(sessionFactory);
    }

}
