package org.grouter.domain.servicelayer.spring;

import static org.grouter.domain.daolayer.DAOFactory.FactoryType.HIBERNATESPRING;
import org.grouter.domain.daolayer.SystemUserDAO;
import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.DAOFactory;
import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.entities.SystemUser;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.servicelayer.GRouterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.Validate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


/**
 * GRouterServiceImpl exposes services for different clients - gswing and gweb.
 *
 * <p/>
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext xml file/s
 * or if you are using Ejb3 in the annotations of the Ejb3 session beans.
 * <p/>
 * This implementation class is injected using Spring IoC container.
 *
 * @author Georges Polyzois
 */
public class GRouterServiceImpl implements GRouterService
{
    private static Log logger = LogFactory.getLog(GRouterServiceImpl.class);
    private MessageDAO messageDAO;
    private NodeDAO nodeDAO;

    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }

//    private SystemUserDAO systemUserDAO;

    public MessageDAO getMessageDAO()
    {
        return messageDAO;
    }

    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public GRouterServiceImpl()
    {
    }

  /*  private void initSystemDAO()
    {
        systemUserDAO = DAOFactory.getFactory(HIBERNATESPRING).getSystemUserDAO();
    }
    */

 /*  private void initMessageDAO()
    {
        logger.info("Initializing the MessageDAOImpl");
        messageDAO = DAOFactory.getFactory(HIBERNATESPRING).getMessageDAO();
    }
   */
    //TODO Can we use AOP to have a simple logging mechnism used by all sevice methods!?


    public Message createMessage(Message message)
    {
        Validate.notNull(message, "In parameter can not be null");
       // initMessageDAO();
        return messageDAO.save(message);
    }

    public Message findMessageById(String id)
    {
        Validate.notNull(id, "In parameter can not be null");

       // initMessageDAO();

        Message foundMessage = messageDAO.findById(id);
        return foundMessage;
    }

    public List<Message> findAllMessages(String nodeId)
    {
        return messageDAO.findMessagesForNode( nodeId );


    }

    public List<Node> findAllNodes(String routerId)
    {
        return nodeDAO.findAll();
    }



}
