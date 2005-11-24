package org.grouter.domain.service.test;

import junit.framework.TestCase;
import org.grouter.common.logging.Log4JInit;
import org.grouter.domain.systemuser.SystemUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: 2005-nov-23
 * Time: 16:20:58
 * To change this template use File | Settings | File Templates.
 */
public class SpringTest extends TestCase
{
    private Log4JInit log4j = new Log4JInit();
    private static Log log = LogFactory.getLog(SpringTest.class);
    private final static String BEANSCONFIGFILE = "spring/applicationContext.xml";
    protected static BeanFactory factory;
    protected static SystemUser systemUser;
    protected static Calendar inThreeDays = GregorianCalendar.getInstance();
    protected static Calendar inFiveDays = GregorianCalendar.getInstance();
    protected static Calendar nextWeek = GregorianCalendar.getInstance();

    static
    {
        factory = new ClassPathXmlApplicationContext(BEANSCONFIGFILE);
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);

        systemUser = new SystemUser("Albert","Albert Einstein","genious",true,3, Calendar.getInstance(),nextWeek );
    }
}
