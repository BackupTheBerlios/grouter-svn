package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.UserDAO;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Sender;
import org.grouter.domain.entities.Receiver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Initialization of test data for the domain model. Data are created as domain objects.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractInMemoryDataInitializationTests extends AbstractInMemoryHibernateInitializationTests
{
    private static Log log = LogFactory.getLog(AbstractInMemoryDataInitializationTests.class);
    public Message message;
    Calendar inThreeDays = GregorianCalendar.getInstance();
    Calendar inFiveDays = GregorianCalendar.getInstance();
    Calendar nextWeek = GregorianCalendar.getInstance();
    Calendar today;


    /**
     * Create test data for our domain model. Domain entities can be used
     * from subclasses for use case driven unit testing.
     */
    protected void initData()
    {
        log.info("Initializing data for unit tests!");

        // Create and save a message
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        Sender sender = new Sender("sender name");
        message = new Message("A message");
        Receiver receiver = new Receiver("A receiver name");
        message.addToReceivers(receiver);
        message.setSender(sender);
        sender.addToMessages(message);
        messageDAO.save(message);

        // Create and save a user
        UserDAO userDAO = DAOFACTORY.getSystemUserDAO();
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);
    }

    /**
     * Call initData and flush session.
     */
    public void inTransaction()
    {
        initData();
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }

}