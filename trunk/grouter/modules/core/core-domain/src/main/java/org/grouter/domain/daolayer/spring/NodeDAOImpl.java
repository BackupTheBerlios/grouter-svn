package org.grouter.domain.daolayer.spring;

import org.grouter.domain.entities.Node;
import org.grouter.domain.daolayer.NodeDAO;
import org.grouter.domain.daolayer.spring.GenericHibernateDAO;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.Criteria;
import org.hibernate.type.Type;
import org.hibernate.criterion.Projections;

import java.util.List;


public class NodeDAOImpl extends GenericHibernateDAO<Node, String> implements NodeDAO
{
    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public NodeDAOImpl()
    {
        super(Node.class);
    }

    public NodeDAOImpl(Session session)
    {
        super(Node.class, session);
    }

    /**
     * {@inheritDoc }
     */
    public List<Node> findNodesWithNumberOfMessages(String routerId)
    {
        String hsql = "from Node obj where obj.router.id = :routerId";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        List<Node> nodes = qr.setParameter("routerId", routerId).list();
        for (Node node : nodes)
        {
            node.setNumberOfMessagesHandled(getNumberOfMessages(node.getId()));
        }
        return nodes;
    }

    public Long getNumberOfMessages(String nodeId)
    {
        String hsql = "select count(*) from Message obj where obj.node.id = :nodeid";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return (Long) qr.setParameter("nodeid", nodeId).uniqueResult();
    }


    // todo inefficient - try using selec count(*)
    public List<Node> findAllNodes(String routerId)
    {
        String hsql = "from Node obj where obj.router.id = :routerId"; 
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        List<Node> nodes = qr.setParameter("routerId", routerId).list();
        
        for (Node node : nodes)
        {
            node.setNumberOfMessagesHandled(getNumberOfMessages(node.getId()));
            Hibernate.initialize( node.getInBound() );
            Hibernate.initialize( node.getOutBound() );
        }
        return nodes;

    }

    public List<Node> findNodes()
    {
        // Todo fix this to return count(*) for a particular node
        String sqlFragment = "(select count(*) from Message i where i.node_fk = node_fk) as numOfItems";
        Criteria criteria = getSession().createCriteria(getEntityClass())
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"))
                        .add(Projections.property("name"))
                        .add(Projections.sqlProjection(
                        sqlFragment, new String[]{"numOfItems"}, new Type[]{Hibernate.LONG}))
                );


        return criteria.list();
    }
}
