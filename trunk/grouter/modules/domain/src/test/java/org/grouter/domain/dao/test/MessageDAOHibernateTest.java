package org.grouter.domain.dao.test;

/**
 * Unit test MessageDAO interface.
 *
 * @author Georges Polyzois
 */

import org.grouter.domain.dao.hibernate.MessageDAOHibernate;
import org.grouter.domain.dao.test.TestCaseWithData;
import org.grouter.domain.dao.MessageDAO;
import org.grouter.domain.Message;
import org.hibernate.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageDAOHibernateTest extends TestCaseWithData
{
    MessageDAOHibernate messageDAOHibernate;
    private static Log log = LogFactory.getLog(MessageDAOHibernateTest.class);

    public void finder() throws Exception
    {
         //MessageDAO messageDAO = DAOFACTORY.getMessageDAO();
        //Message amessage = messageDAO.findById(super.message.getId(), false);

    }


    public void testSaveMessage() throws Exception
    {
    }
}