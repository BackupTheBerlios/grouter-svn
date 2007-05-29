package org.grouter.domain.daolayer;

import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;

import java.util.List;

/**
 * Business DAO operations related to the <tt>Node</tt> entity.
 * <p/>
 *
 * @author Georges Polyzois
 */
public interface NodeDAO extends GenericDAO<Node, String>
{
    /**
     * Use to get all all nodes for a given router with number of messages set on each node.
     * 
     * @param routerId get statistics for this router
     * @return list with Nodes and number of messages for every node
     */
    List<Node> findNodesWithNumberOfMessages( String routerId );


    Long getNumberOfMessages( String nodeId );

    List<Node> findAllNodes(String routerId);

    List<Node> findNodes();
}