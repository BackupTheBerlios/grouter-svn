package org.grouter.domain.daolayer.spring;

import org.grouter.domain.daolayer.RouterDAO;
import org.grouter.domain.daolayer.spring.GenericHibernateDAO;
import org.grouter.domain.entities.Router;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Projection;

import java.util.List;

/**
 * @author Georges
 */
public class RouterDAOImpl extends GenericHibernateDAO<Router, String> implements RouterDAO
{


    protected RouterDAOImpl()
    {
        super(Router.class);
    }

    protected RouterDAOImpl(Class<Router> persistentClass)
    {
        super(persistentClass);
    }

    public RouterDAOImpl(Class<Router> persistentClass, Session session)
    {
        super(persistentClass, session);
    }

    public List<Router> findAllDistinct()
    {

        String hsql = "from Router";
        Session session = getSession();
        Query qr = session.createQuery(hsql);
        return qr.list();
    }

 /*   public List<Router> findByProjection()
    {
        Criteria qriteria = getSession().createCriteria(getEntityClass())
                .setProjection(Projections.projectionList()
                        .add(Projections.id())
                        .add(Projections.property("name")));
        return qriteria.list();


    }

    */
}
