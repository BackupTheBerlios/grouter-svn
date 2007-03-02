package org.grouter.domain.servicelayer;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.SystemUser;
import org.grouter.domain.entities.Node;

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
 * Main interface for operations with the grouter internal domain.
 *
 * There are a spring based implementation and
 *
 * @author Georges Polyzois
 */
@Remote
@Local
public interface GRouterService
{

    /**
     * Stores a message - all relationships need to be inplace for persitence operation is to succeed.
     * @param message a message to persist
     * @return
     */
    Message createMessage(Message message);

    /**
     *
     * @param id
     * @return
     */
    Message findMessageById(String id);

    /**
     * Find messages for this node.
     * @param id a node for which we want all messages
     * @return a list of {@link Message}s
     */
    List<Message> findAllMessages( String id);

    /**
     *
     * @param routerId
     * @return
     */
    List<Node> findAllNodes( String routerId );

}
