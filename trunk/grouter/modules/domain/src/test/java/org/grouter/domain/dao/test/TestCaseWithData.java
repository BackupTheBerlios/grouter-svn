package org.grouter.domain.dao.test;

import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.domain.systemuser.Password;
import org.grouter.domain.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * No testing goes on in this class - only initialization of test data in the
 * domain model.
 *
 * @author Georges Polyzois
 */
public abstract class TestCaseWithData extends HibernateTest
{
    private static Log log = LogFactory.getLog(TestCaseWithData.class);
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
           /*
        MessageDAO messageDAO = DAOFACTORY.getMessageDAO();

        Sender sender = new Sender("sender name");
        sessionFactory.getCurrentSession().saveOrUpdate(sender);

        message = new Message("A message",null,sender);
        Receiver receiver = new Receiver("A receiver name","id");
        message.addToReceivers(receiver);
        messageDAO.saveOrUpdate(message);
         */
        SystemUserDAO systemUserDAO = DAOFACTORY.getSystemUserDAO();
        inThreeDays.roll(Calendar.DAY_OF_YEAR, 3);
        inFiveDays.roll(Calendar.DAY_OF_YEAR, 5);
        nextWeek.roll(Calendar.WEEK_OF_YEAR, true);

        // Categories
        systemUser1 = new SystemUser("Donald", "Donald Duck", "is funny", true, 3, today, nextWeek);
        systemUser1Password = new Password(systemUser1, "password");
        systemUser1.addPassword(systemUser1Password);

        systemUserDAO.saveOrUpdate(systemUser1);

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