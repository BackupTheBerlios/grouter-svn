package org.grouter.presentation.controller.node;

import org.grouter.domain.entities.Node;

/**
 * @author Georges Polyzois
 */
public class NodeCommand
{
    Node node = new Node();


    public NodeCommand()
    {
    }

    public NodeCommand(Node node)
    {
        this.node = node;
    }


    public Node getNode()
    {
        return node;
    }

    public void setNode(Node node)
    {
        this.node = node;
    }
}
