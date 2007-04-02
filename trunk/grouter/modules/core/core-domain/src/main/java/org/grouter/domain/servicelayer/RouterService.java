package org.grouter.domain.servicelayer;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.Router;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-18
 * Time: 12:35:20
 * To change this template use File | Settings | File Templates.
 */

import javax.ejb.Remote;
import javax.ejb.Local;
import java.util.List;


/**
 * Main interface for operations with the grouter service layer.
 *
 * @author Georges Polyzois
 */
@Remote
@Local
public interface RouterService
{
    final static String BEANNAME = "routerService";

    /**
     * Retrieve a list with all grouters available - loads all collections if specified in mapping.
     *
     * @return a "fully" loaded router as per mapping definition
     */
    List<Router> findAll();


    List<Router> findAllDistinct();


    /**
     * Stores a message - all relationships need to be inplace for persitence operation is to succeed.
     *
     * @param message a message to persist
     * @return
     */
    void saveMessage(Message message);

    /**
     * Finder.
     * @param id of a Message enitty
     * @return
     */
    Message findMessageById(String id);

    /**
     * Find messages for this node.
     *
     * @param nodeId a node for which we want all messages
     * @return a list of {@link Message}s
     */
    List<Message> findAllMessages(String nodeId);

    /**
     * @param routerId
     * @return
     */
    List<Node> findAllNodes(String routerId);


    /**
     * Stores a router - all relationships need to be inplace for persitence operation is to succeed.
     * Typically used when a router instance starts up and reads in a configuration file.
     *
     * @param router a router to persist
     * @return
     */
    void saveRouter(Router router);


    /**
     * Stores a node - all relationships need to be inplace for persitence operation is to succeed.
     * Altering a node does not persiste between restarts of a Router, since the master config file
     * is read on every restart of the router.
     *
     * TODO should we overwrite a config file for a router (backing up the previous one)?!?
     *
     * @param node
     */
    void saveNode(Node node);


    /**
     * Retrieve number of messages for every node on this router instance - using select count(*).
     * 
     * @param routerId id of the node we want to query
     * @return list {@link Node}s with number of messages set
     */
    List<Node> findNodesWithNumberOfMessages( String routerId );


    Node findById( String nodeId);

}
