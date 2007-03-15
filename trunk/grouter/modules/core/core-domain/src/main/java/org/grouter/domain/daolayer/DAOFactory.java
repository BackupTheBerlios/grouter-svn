package org.grouter.domain.daolayer;

import org.grouter.domain.daolayer.ejb3.Ejb3DAOFactory;
import org.grouter.domain.daolayer.spring.HibernateDAOFactory;

/**
 * See Caveat
 * <p/>
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
 * <p/>
 * See the Hibernate Caveat tutorial and complementary code by Christian Bauer @ jboss
 *
 * @author Georges Polyzois
 */
public abstract class DAOFactory
{
    // public static final DAOFactory EJB3_PERSISTENCE = new org.hibernate.ce.auction.dao.ejb3.org.grouter.domain.daolayer.ejb3.Ejb3DAOFactory();
    public static DAOFactory HIBERNATE = new HibernateDAOFactory();
    public static DAOFactory DEFAULT = HIBERNATE;
    /* Name of special Spring bean */
    private static final String HIBERNATE_SPRING_DAOFACTORY = "hibernateSpringDAOFactory";

    public enum FactoryType
    {
        EJB3_PERSISTENCE, HIBERNATE, HIBERNATESPRING
    }

    /**
     * Creates three different types of factories depenging on type of technology used.
     *
     * @param factoryType
     * @return
     */
    public static DAOFactory getFactory(FactoryType factoryType)
    {
        switch (factoryType)
        {
            case EJB3_PERSISTENCE:
            {
                return new Ejb3DAOFactory();
            }
            case HIBERNATE:
            {
                return new HibernateDAOFactory();
            }
            /*case HIBERNATESPRING:
            {
                BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance("context-wrapper.xml");
                BeanFactoryReference bf = bfl.useBeanFactory("org.grouter");
                return (HibernateSpringDAOFactory) bf.getFactory().getBean(HIBERNATE_SPRING_DAOFACTORY);
                //GlobalBeanLocator globalBeanLocator = GlobalBeanLocator.getInstance();
                //return (HibernateSpringDAOFactory)globalBeanLocator.getApplicationContext().getBean(HIBERNATE_SPRING_DAOFACTORY);
            } */
        }
        return null;
    }


    // Add your DAO interfaces here
    public abstract MessageDAO getMessageDAO();

    public abstract UserDAO getSystemUserDAO();


}
