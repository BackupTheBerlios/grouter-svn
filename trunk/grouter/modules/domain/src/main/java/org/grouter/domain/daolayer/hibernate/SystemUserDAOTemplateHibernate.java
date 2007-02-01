package org.grouter.domain.daolayer.hibernate;

import org.apache.log4j.Logger;
import org.grouter.common.hibernate.HibernateTemplate;
import org.grouter.common.hibernate.HibernateTemplateHandler;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.SystemUser;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Hibernate-specific implementation of the <tt>SystemUserDAO</tt>
 * non-CRUD data access object.
 * <p/>
 * This implementation uses the HibernateTemplate and HibernateTemplateHandler
 * classes from the package org.grouter.common. Template method pattern hiding skeleton
 * code of opening closing sessions etc. Use from all Hibernate DAO implementation
 * classes to centralize exception handling and session opening and closing.
 */
public class SystemUserDAOTemplateHibernate //extends GenericHibernateDAO<SystemUser, Long> implements SystemUserDAO
{
    Logger logger = Logger.getLogger(SystemUserDAOTemplateHibernate.class);


    public SystemUserDAOTemplateHibernate(Session session)
    {
        //     super(SystemUser.class, session);
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
        final SystemUser systemUser = new SystemUser(userName, fullName, description, active, loginRetries, validFrom, validTo);
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
            logger.error(e, e);
        }
    }

    public Long createSystemUser(SystemUser systemUser)
    {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    public void removeUser(Long id)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void removeUser(String userName)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean userExists(String userName)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean userExists(Long id)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
