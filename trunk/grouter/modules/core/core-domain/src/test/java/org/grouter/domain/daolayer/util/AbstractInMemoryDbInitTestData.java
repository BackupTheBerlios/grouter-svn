package org.grouter.domain.daolayer.util;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.entities.systemuser.SystemUser;
import org.grouter.domain.entities.systemuser.Password;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Receiver;
import org.grouter.domain.entities.Sender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * No testing goes on in this class - only initialization of test data in the
 * domain model.
 *
 * @author Georges Polyzois
 */
public abstract class AbstractInMemoryDbInitTestData extends AbstractInMemoryDbTestCase
{
    private static Log log = LogFactory.getLog(AbstractInMemoryDbInitTestData.class);
    public Message message;
    SystemUser systemUser1;
    Password systemUser1Password;
    SystemUser systemUser2;
    Password systemUser2Password;
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
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);
        systemUser1 = new SystemUser("Donald", "Donald Duck", "is funny", true, 3, today, nextWeek);
        systemUser1Password = new Password(systemUser1, "1password");
        systemUser1.addPassword(systemUser1Password);
        systemUserDAO.save(systemUser1);
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