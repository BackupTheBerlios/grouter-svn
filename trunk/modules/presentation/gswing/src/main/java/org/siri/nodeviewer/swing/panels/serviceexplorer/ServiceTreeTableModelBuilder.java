package org.siri.nodeviewer.swing.panels.serviceexplorer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Georges Polyzois
 * @version $Revision: $
 * @since $Date: $
 */
public class ServiceTreeTableModelBuilder
{
    ServiceNode rootNode = new ServiceNode();
    ServiceNode currentNode = new ServiceNode();

    public ServiceTreeTableModelBuilder(ServiceNode rootNode)
    {
        this.rootNode = rootNode;
        this.currentNode = rootNode;
    }

    public void addChild(ServiceNode child)
    {
        addTo(currentNode, child);
    }


    public void addSibling(ServiceNode siblingNode)
    {
        addTo((ServiceNode )currentNode.getParent(), siblingNode);
    }

    public void addToParent(ServiceNode parentNode, ServiceNode childNode)
    {
        addTo(findParentBy(parentNode),childNode);
    }

    private ServiceNode findParentBy(ServiceNode serviceNode)
    {
        ServiceNode parentServiceNode = currentNode;
        while(parentServiceNode != null)
        {
            if(serviceNode == parentServiceNode)
            {
                return parentServiceNode;
            }
            parentServiceNode = (ServiceNode)parentServiceNode.getParent();
        }
        return null;
    }

    private void addTo(ServiceNode parentNode, ServiceNode childNode)
    {
        this.currentNode = childNode;
        parentNode.addChild(currentNode);
    }


    public ServiceTreeTableModel getServiceTreeTableModel()
    {
        ArrayList<ServiceNode> rows = new ArrayList<ServiceNode>();
        rows.add(rootNode);
        return new ServiceTreeTableModel(rows);
        /*
          ServiceNodeItem nodeItemNode1 = new ServiceNodeItem("Feeder1", "snode 1", "", "", IconFactory.getImageIcon(Constants.NETWORK));
            ServiceNodeItem nodeItemNode1Child1 = new ServiceNodeItem("Feeder1", "snode 2", "service-kalle-child1", "runing", IconFactory.getImageIcon(Constants.FLAG));
            ServiceNodeItem nodeItemNode1Child2 = new ServiceNodeItem("Feeder1", "snode 3", "service-kalle-child2", "runing", IconFactory.getImageIcon(Constants.FLAG));

            ServiceNode nodeRowChild1 = new ServiceNode(nodeItemNode1Child1);
            ServiceNode nodeRowChild2 = new ServiceNode(nodeItemNode1Child2);

            ArrayList children = new ArrayList();
            children.add(nodeRowChild1);
            children.add(nodeRowChild2);

            ServiceNode root1 = new ServiceNode(nodeItemNode1);
            root1.setChildren(children);

            ///////
            ServiceNodeItem nodeItemNode2 = new ServiceNodeItem("Feeder2", "snode 1", "", "", IconFactory.getImageIcon(Constants.NETWORK));
            ServiceNodeItem nodeItemNode2Child1 = new ServiceNodeItem("Feeder2", "snode 2", "service-maja-child1", "runing", IconFactory.getImageIcon(Constants.FLAG));
            ServiceNodeItem nodeItemNode2Child2 = new ServiceNodeItem("Feeder2", "snode 3", "service-nisss-child2", "stopped", IconFactory.getImageIcon(Constants.FLAG));

            ServiceNode node2RowChild1 = new ServiceNode(nodeItemNode2Child1);
            ServiceNode node2RowChild2 = new ServiceNode(nodeItemNode2Child2);

            ArrayList children2 = new ArrayList();
            children2.add(node2RowChild1);
            children2.add(node2RowChild2);

            ServiceNode root2 = new ServiceNode(nodeItemNode2);
            root2.setChildren(children2);


            ArrayList<ServiceNode> rows = new ArrayList<ServiceNode>();
            rows.add(root1);
            rows.add(root2);

            return new  ServiceTreeTableModel(rows);
        */
    }


    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.rootNode.getId());
        stringBuilder.append("\n");
        List children = this.rootNode.getChildren();
        for (int i = 0; i < children.size(); i++)
        {
            stringBuilder.append( ((ServiceNode)children.get(i)).getId());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
