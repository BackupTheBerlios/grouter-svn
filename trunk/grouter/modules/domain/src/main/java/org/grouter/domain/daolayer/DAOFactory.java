package org.grouter.domain.daolayer;

import org.grouter.common.jndi.GlobalBeanLocator;
import org.grouter.domain.daolayer.hibernate.HibernateSpringDAOFactory;
import org.grouter.domain.daolayer.ejb3.Ejb3DAOFactory;
import org.grouter.domain.daolayer.hibernate.HibernateDAOFactory;

/**
 * See Caveat
 *
 * Defines all DAOs and the concrete factories to get the conrecte DAOs.
 * <p/>
 * Either use the <tt>DEFAULT</tt> to get the same concrete DAOFactory
 * throughout your application, or a concrete factory by name, e.g.
 * <tt>DAOFactory.HIBERNATE</tt> is a concrete <tt>HibernateDAOFactory</tt>.
 * <p/>
 * Implementation: If you write a new DAO, this class has to know about it.
 * If you add a new persistence mechanism, add an additional concrete factory
 * for it to the enumeration of factories.
 * <p/>
 *
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss
 *
 * @author Georges Polyzois
 */
public abstract class DAOFactory
{
    // public static final DAOFactory EJB3_PERSISTENCE = new org.hibernate.ce.auction.dao.ejb3.org.grouter.domain.daolayer.ejb3.Ejb3DAOFactory();
    public static DAOFactory HIBERNATE = new org.grouter.domain.daolayer.hibernate.HibernateDAOFactory();
    public static DAOFactory DEFAULT = HIBERNATE;
    private static final String HIBERNATE_SPRING_DAOFACTORY = "hibernateSpringDAOFactory";

    public enum FactoryType {EJB3_PERSISTENCE,HIBERNATE,HIBERNATESPRING}

    public static DAOFactory getFactory(FactoryType factoryType)
    {
        switch(factoryType)
        {
            case EJB3_PERSISTENCE:
            {
                return new Ejb3DAOFactory();
            }
            case HIBERNATE:
            {
                return new HibernateDAOFactory();
            }
            case HIBERNATESPRING:
            {
                return (HibernateSpringDAOFactory)GlobalBeanLocator.getBeanBeanFactory().getBean(HIBERNATE_SPRING_DAOFACTORY);
            }
        }
        return null;
    }


    // Add your DAO interfaces here
    public abstract MessageDAO getMessageDAO();
    public abstract SystemUserDAO getSystemUserDAO();


}
