package org.grouter.common.hibernate;

import org.hibernate.Session;


/**
 * Interface used for callbacks to DAO implementation.
 *
 */
public interface HibernateTemplate
{

    /**
     * Implemented for inversion.
     * @param session Session
     * @return Object
     * @throws Exception
     */
    Object handle(Session session) throws Exception;
}
