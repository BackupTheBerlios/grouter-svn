package org.grouter.presentation.util;

import org.apache.log4j.Logger;
import org.grouter.common.hibernatesearch.FullIndexHandler;
import org.grouter.domain.entities.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import javax.servlet.ServletContextEvent;

/**                                 
 * On load of the application we want to create the search index for Hibernate.
 *
 * This class is registered in web.xml as a listener.
 *
 * @author Georges Polyzois
 */
public class HibernateSearchIndexContextListener extends WebApplicationObjectSupport
{
    private static Logger logger = Logger.getLogger(HibernateSearchIndexContextListener.class);

    public void contextInitialized(ServletContextEvent event)
    {
        logger.info("Initializing the search index");

        FullIndexHandler fullIndexHandler = new FullIndexHandler();
        fullIndexHandler.doFullIndex(1000, Message.class, getSession());
    }

    public void contextDestroyed(ServletContextEvent event)
    {
        
    }


    protected Session getSession() {
        return ((SessionFactory) getApplicationContext().getBean("sessionFactory")).getCurrentSession();
    }

}
