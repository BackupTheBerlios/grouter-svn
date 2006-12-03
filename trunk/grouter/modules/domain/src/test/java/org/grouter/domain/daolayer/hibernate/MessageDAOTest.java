package org.grouter.domain.daolayer.hibernate;

import org.grouter.domain.entities.Message;

import java.util.List;

/**
 * Test on the MessageDAOImpl. Uses Spring to inject the DAO and load the application
 * context files. For every test run it loads the sql statements from a file and
 * populates the database - and after the method it tears it down again.
 *
 * Call endTransaction to terminate a session and finish up the transaction. After
 * that try testing what has been loaded into the object graph.
 *
 *
 *
 */
public class MessageDAOTest extends AbstractDaoTest
{
    private static final String NODEID = "nodeid_2";

    MessageDAOImpl messageDAO;

    /**
     * Injected.
     *
     * @param messageDAO
     */
    public void setMessageDAO(MessageDAOImpl messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    @Override
    protected String getTestDataLocation()
    {
        return "sql/messageDAOtest.sql";
    }


    public void testFindById()
    {
        List<Message> messages = messageDAO.findMessagesForNode(NODEID);
        assertNotNull(messages);
        assertEquals(7, messages.size());
    }

}
