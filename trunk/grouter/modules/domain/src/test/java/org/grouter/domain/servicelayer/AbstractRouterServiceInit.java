package org.grouter.domain.servicelayer;

import junit.framework.TestCase;
import org.grouter.common.jndi.GlobalBeanLocator;
import org.grouter.common.jndi.JNDIUtils;
import org.grouter.domain.entities.systemuser.SystemUser;
import org.grouter.domain.entities.Message;
import org.grouter.domain.servicelayer.ejb3.GRouterRemoteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Initialize Spring and setup up some basic data for unit tests.
 */
public abstract class AbstractRouterServiceInit extends TestCase
{
    //private Log4JInit log4j = new Log4JInit();
    private static Log logger = LogFactory.getLog(AbstractRouterServiceInit.class);
    private final static String BEANSCONFIGFILE = "ac-service.xml";
    protected static BeanFactory factory;
    protected static SystemUser systemUser;
    protected static Message message;
    protected static Calendar inThreeDays = GregorianCalendar.getInstance();
    protected static Calendar inFiveDays = GregorianCalendar.getInstance();
    protected static Calendar nextWeek = GregorianCalendar.getInstance();
    protected static Context context;
    protected static boolean isSpringTest = true;
    private final static String JNDINAME = "domain/GRouterBeanService/remote";
    protected static GRouterRemoteService remoteRouter;

    public AbstractRouterServiceInit()
    {
        init();
    }

    private void init()
    {
        if (isSpringTest)
        {
            logger.info("Using Spring for testing servce layer");
            // Spring init
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext(BEANSCONFIGFILE);
                    factory = (BeanFactory) applicationContext;
           GlobalBeanLocator.getInstance().setApplicationContext(applicationContext);
        }
        else
        {
            try
            {
                context = JNDIUtils.getJbossInitialContext();
                //ServiceLocator serviceLocator = ServiceLocator.getInstance().

               
                Object obj = context.lookup(JNDINAME);
                remoteRouter = (GRouterRemoteService) PortableRemoteObject.narrow( obj, GRouterRemoteService.class );

            } catch (NamingException e)
            {
                logger.error(e,e);
                assertTrue(false);
                System.exit(0);

            }
        }
        // Data
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);

        systemUser = new SystemUser("Albert", "Albert Einstein", "genious", true, 3, Calendar.getInstance(), nextWeek);

         initMessage();

    }

    private static void initMessage()
    {
        message = RouterMessageFactory.createRouter();
    }
}
