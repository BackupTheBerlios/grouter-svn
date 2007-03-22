package org.grouter.domain.servicelayer.spring;

import org.grouter.domain.daolayer.MessageDAO;
import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.daolayer.RouterDAO;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;
import org.grouter.domain.servicelayer.RouterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.Validate;

import java.util.List;


/**
 * The implementation of the interface {@link org.grouter.domain.servicelayer.RouterService} uses underlying
 * generic DAOs providing transaction demarcation for the service layer.
 *
 * Client such as - gswing and gweb - uses this service layer.
 *
 * Methods and their transaction demarcation attributes are handled in the Spring applicationContext xml file/s
 * or if you are using Ejb3 in the annotations of the Ejb3 session beans.
 *
 * DAOs are injected using Spring.
 *
 * @author Georges Polyzois
 */
public class RouterServiceImpl implements RouterService
{
    private static Log logger = LogFactory.getLog(RouterServiceImpl.class);
    private MessageDAO messageDAO;
    private NodeDAO nodeDAO;
    private RouterDAO routerDAO;

    public void setRouterDAO(RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }

    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }

    public MessageDAO getMessageDAO()
    {
        return messageDAO;
    }

    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }

    public RouterServiceImpl()
    {
    }


    public List<Router> findAll()
    {
        return routerDAO.findAll();  
    }

    public void saveMessage(Message message)
    {
        Validate.notNull(message, "In parameter can not be null");
        messageDAO.save(message);
    }

    public Message findMessageById(String id)
    {
        Validate.notNull(id, "In parameter can not be null");
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

    public void saveRouter(Router router)
    {
        Validate.notNull(router, "In parameter can not be null");
        routerDAO.save(router);
    }
}
