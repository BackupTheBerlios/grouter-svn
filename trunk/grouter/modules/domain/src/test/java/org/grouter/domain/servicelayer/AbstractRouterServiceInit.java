package org.grouter.domain.servicelayer;

import junit.framework.TestCase;
import org.grouter.common.logging.Log4JInit;
import org.grouter.common.jndi.GlobalBeanLocator;
import org.grouter.common.jndi.JNDIUtils;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.domain.*;
import org.grouter.domain.servicelayer.ejb3.GRouterRemote;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;
import java.sql.Timestamp;

/**
 * Initialize Spring and setup up some basic data for unit tests.
 */
public abstract class AbstractRouterServiceInit extends TestCase
{
    private Log4JInit log4j = new Log4JInit();
    private static Log logger = LogFactory.getLog(AbstractRouterServiceInit.class);
    private final static String BEANSCONFIGFILE = "spring/applicationContext.xml";
    protected static BeanFactory factory;
    protected static SystemUser systemUser;
    protected static Message message;
    protected static Node node;
    protected static Router router;
    protected static Calendar inThreeDays = GregorianCalendar.getInstance();
    protected static Calendar inFiveDays = GregorianCalendar.getInstance();
    protected static Calendar nextWeek = GregorianCalendar.getInstance();
    protected static Context context;
    protected static boolean isSpringTest = true;
    private final static String JNDINAME = "domain/GRouterBean/remote";
    protected static GRouterRemote remoteRouter;

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
            factory = new ClassPathXmlApplicationContext(BEANSCONFIGFILE);
            GlobalBeanLocator.setBeanFactory(factory);
        }
        else
        {
            try
            {
                context = JNDIUtils.getJbossInitialContext();
                //ServiceLocator serviceLocator = ServiceLocator.getInstance().

               
                Object obj = context.lookup(JNDINAME);
                remoteRouter = (GRouterRemote) PortableRemoteObject.narrow( obj, GRouterRemote.class );

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

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Sender sender = new Sender("A test sender");
        message = new Message("A test message");
        Receiver receiver = new Receiver("A test receiver");
        message.addToReceivers(receiver);
        message.setSender(sender);
        logger.debug("##" + timestamp);
        message.setCreationTimestamp(timestamp);
        sender.addToMessages(message);

        Set<Message> messages = new HashSet<Message>();
        messages.add(message);

        node = new Node("nodename", messages, timestamp, null);
        message.setNode(node);


        Set<Node> nodes = new HashSet<Node>();
        nodes.add(node);

        router = new Router("mytestrouter", nodes, timestamp, 1000);
        node.setRouter(router);


    }
}
