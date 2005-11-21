/**
 * HibernateManager.java gggg
 */
package org.grouter.common.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;

/**
 * Template method pattern hiding skeleton code of opening closing sessions
 * etc. Use from all Hibernate DAO implementation classes to centralize exception
 * handling and session opening and closing.
 *
 */
public class HibernateTemplateHandler
{ 
    /**
     * Logger.
     */
    private static Logger logger = Logger.getLogger(HibernateTemplateHandler.class);
    /**
     * Hibernate util instance.
     */
    HibernateUtil myHibernateUtil;

    /**
     * Used to hide all hideous exception handling and encapsulating all
     * MUST DO things like closing sessions and gracefully handing back
     * resources.
     *
     * @param handler HibernateHandler
     * @throws java.lang.Exception if session, transaction or mapping failes
     * @return Object
     */
    public Object accept(HibernateTemplate handler) throws Exception
    {
        Object result = null;
        Session session = myHibernateUtil.getSession();
        try
        {
            myHibernateUtil.beginTransaction();
            result = handler.handle(session);
            session.flush();
            myHibernateUtil.commitTransaction();
        }
        catch (MappingException ex)
        {
            myHibernateUtil.rollbackTransaction();
            throw new Exception("Mapping failure in Hibernate", ex);
        }
        catch (HibernateException ex)
        {
            myHibernateUtil.rollbackTransaction();
            throw new Exception("Transaction rollbacked. Hibernate reported exception.", ex);
        }
        catch (Exception ex)
        {
            myHibernateUtil.rollbackTransaction();
            throw new Exception("Transaction rollbacked. Exception caught.", ex);
        }
        finally
        {
            myHibernateUtil.closeSession();
        }
        return result;
    }
}
