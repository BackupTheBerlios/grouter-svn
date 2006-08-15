package org.grouter.domain.servicelayer.spring;

import junit.framework.TestCase;
import org.grouter.common.logging.Log4JInit;
import org.grouter.common.jndi.GlobalBeanLocator;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.domain.Sender;
import org.grouter.domain.Message;
import org.grouter.domain.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Timestamp;

/**
 *  Initialize Spring and setup up some basic data for unit tests.
 *
 */
public abstract class AbstractGrouterServiceInit extends TestCase
{
    private Log4JInit log4j = new Log4JInit();
    private static Log log = LogFactory.getLog(AbstractGrouterServiceInit.class);
    private final static String BEANSCONFIGFILE = "spring/applicationContext.xml";
    protected static BeanFactory factory;
    protected static SystemUser systemUser;
    protected static Message message;
    protected static Calendar inThreeDays = GregorianCalendar.getInstance();
    protected static Calendar inFiveDays = GregorianCalendar.getInstance();
    protected static Calendar nextWeek = GregorianCalendar.getInstance();

    static
    {
        // Spring init
        factory = new ClassPathXmlApplicationContext(BEANSCONFIGFILE);
        GlobalBeanLocator.setBeanFactory(factory);

        // Data
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);

        systemUser = new SystemUser("Albert","Albert Einstein","genious",true,3, Calendar.getInstance(),nextWeek );

        initMessage();
    }

    private static void initMessage()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = new Sender("A test sender");
        message = new Message("A test message" );
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        log.debug("##" + timestamp);
        message.setCreationTimestamp(timestamp);
        sender.addToMessages(message);
    }
}
