package org.grouter.presentation.beans;

import org.grouter.domain.entities.Node;
import org.grouter.domain.servicelayer.GRouterService;
import org.apache.log4j.Logger;

/**
 * @author Georges Polyzois
 */
public class NodeBean
{
    private static Logger logger = Logger.getLogger(NodeBean.class);
    Node node;
    GRouterService grouterService;
    String id;
    String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setId(String id)
    {
        this.id = id;
    }


    public void setGrouterService(GRouterService grouterService)
    {
        this.grouterService = grouterService;
    }

    public String submitSave()
    {
        logger.info("###############  Submitting form");
        return "sucess";

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
