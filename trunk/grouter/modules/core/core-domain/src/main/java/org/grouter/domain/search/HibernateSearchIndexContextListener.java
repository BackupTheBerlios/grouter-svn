package org.grouter.domain.search;

import org.apache.log4j.Logger;
import org.grouter.domain.service.SystemService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * On load of the application we want to create the search index for Hibernate SystemServiceImpl.
 * <p/>
 * This class is registered in web.xml as a listener.
 *
 * @author Georges Polyzois
 */
public class HibernateSearchIndexContextListener implements ApplicationContextAware // WebApplicationObjectSupport
{
    SystemService systemService;
    private ApplicationContext applicationContext;

    private static Logger logger = Logger.getLogger(HibernateSearchIndexContextListener.class);


    // override hook method
    public void initApplicationContext() throws BeansException
    {
        initialize();
    }

    public void initialize()
    {
        logger.info("##################Initializing the search index");
        systemService.initIndex();
    }


    protected Session getSession()
    {
        SessionFactory sessionFactory = ((SessionFactory) applicationContext.getBean("sessionFactory"));

        return sessionFactory.getCurrentSession();
        //return ((SessionFactory) getApplicationContext().getBean("sessionFactory")).getCurrentSession();
    }


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

        this.applicationContext = applicationContext;
    }

    public void setSystemService(SystemService systemService)
    {
        this.systemService = systemService;
    }
}
