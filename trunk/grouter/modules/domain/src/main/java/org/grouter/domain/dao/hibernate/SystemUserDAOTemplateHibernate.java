package org.grouter.domain.dao.hibernate;

import org.hibernate.*;
import org.grouter.domain.dao.SystemUserDAO;
import org.grouter.domain.Message;
import org.grouter.domain.systemuser.SystemUser;
import org.grouter.common.hibernate.HibernateTemplate;
import org.grouter.common.hibernate.HibernateTemplateHandler;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Hibernate-specific implementation of the <tt>SystemUserDAO</tt>
 * non-CRUD data access object.
 *
 * This implementation uses the HibernateTemplate and HibernateTemplateHandler
 * classes from the package org.grouter.common. Template method pattern hiding skeleton
 * code of opening closing sessions etc. Use from all Hibernate DAO implementation
 * classes to centralize exception handling and session opening and closing.
 *
 *
 */
public class SystemUserDAOTemplateHibernate extends GenericHibernateDAO<SystemUser, Long> implements SystemUserDAO
{
    Logger logger = Logger.getLogger(SystemUserDAOTemplateHibernate.class);


    public SystemUserDAOTemplateHibernate(Session session)
    {
        super(SystemUser.class, session);
    }

    @SuppressWarnings("unchecked")
    public List<Message> findConcrete(final Class concreteClass)
    {
        /*
        final List result = new ArrayList();
        HibernateTemplate hibernateTemplate = new HibernateTemplate()
        {
            public Object handle(Session session) throws Exception
            {
                result = session.createCriteria(concreteClass).list();
                return null;
            }
        };

        HibernateTemplateHandler hibernateTemplateHandler = new HibernateTemplateHandler();
        try
        {
            result =  hibernateTemplateHandler.accept(hibernateTemplate);
        } catch (Exception e)
        {
            logger.error(e,e);
        }
        return result;
        */ 
    	return null;
    }

    public void createSystemUser(String userName, String password, String fullName, String description, Calendar validFrom,
                                 Calendar validTo, boolean active, boolean temporaryPassword, int loginRetries, Calendar passwordLastChanged)
    {
        logger.debug("Trying to store new user");
        final SystemUser systemUser = new SystemUser(userName,fullName,description,active,loginRetries,validFrom,validTo);
        HibernateTemplate hibernateTemplate = new HibernateTemplate()
        {
            public Object handle(Session session) throws Exception
            {
                session.save(systemUser);
                return null;
            }
        };

        HibernateTemplateHandler hibernateTemplateHandler = new HibernateTemplateHandler();
        try
        {
            hibernateTemplateHandler.accept(hibernateTemplate);
        } catch (Exception e)
        {
            logger.error(e,e);
        }
    }
}
