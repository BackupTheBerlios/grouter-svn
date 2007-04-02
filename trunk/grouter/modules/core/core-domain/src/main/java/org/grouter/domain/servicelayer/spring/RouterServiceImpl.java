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

    /**
     * Constructor.
     */
    public RouterServiceImpl()
    {

    }

    /**
     * Injected.
     * @param routerDAO injecteed DAO
     */
    public void setRouterDAO(RouterDAO routerDAO)
    {
        this.routerDAO = routerDAO;
    }

    /**
     * Injected.
     * @param nodeDAO injected DAO
     */
    public void setNodeDAO(NodeDAO nodeDAO)
    {
        this.nodeDAO = nodeDAO;
    }


    /**
     * Injected.
     * @param messageDAO injected DAO
     */
    public void setMessageDAO(MessageDAO messageDAO)
    {
        this.messageDAO = messageDAO;
    }


    /**
     * {@inheritDoc}
     */
    public List<Router> findAll()
    {
        return routerDAO.findAll();
    }


    /**
     * {@inheritDoc}
     */
    public List<Router> findAllDistinct()
    {
        return routerDAO.findAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public void saveMessage(Message message)
    {
        Validate.notNull(message, "In parameter can not be null");
        messageDAO.save(message);
    }

    /**
     * {@inheritDoc}
     */
    public Message findMessageById(String id)
    {
        Validate.notNull(id, "In parameter can not be null");
        Message foundMessage = messageDAO.findById(id);
        return foundMessage;
    }


    /**
     * {@inheritDoc}
     */
    public List<Message> findAllMessages(String nodeId)
    {
        return messageDAO.findMessagesForNode( nodeId );
    }


    /**
     * {@inheritDoc}
     */
    public List<Node> findAllNodes(String routerId)
    {
        return nodeDAO.findAllNodes( routerId );
    }


    /**
     * {@inheritDoc}
     */
    public void saveRouter(Router router)
    {
        Validate.notNull(router, "In parameter can not be null");
        routerDAO.save(router);
    }

    /**
     * {@inheritDoc}
     */
    public void saveNode(Node node)
    {
        Validate.notNull(node, "In parameter can not be null");
        nodeDAO.save(node);
    }

    /**
     * {@inheritDoc }
     */
    public List<Node> findNodesWithNumberOfMessages(String routerId)
    {
        return nodeDAO.findNodesWithNumberOfMessages( routerId );  
    }


    /**
     * {@inheritDoc}
     */
    public Node findById(String nodeId)
    {
        return nodeDAO.findById( nodeId );
    }


}
