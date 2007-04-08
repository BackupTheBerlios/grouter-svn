package org.grouter.domain.daolayer.spring;

import org.apache.log4j.Logger;
import org.grouter.domain.daolayer.UserDAO;
import org.grouter.domain.daolayer.RoleDAO;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.Role;
import org.hibernate.Session;

import java.util.List;

/**
 * Hibernate-specific implementation of the {@link org.grouter.domain.daolayer.RoleDAO} interface.
 *
 * @author Georges Polyzois
 */
public class RoleDAOImpl extends GenericHibernateDAO<Role, Long> implements RoleDAO
{
    Logger logger = Logger.getLogger(RoleDAOImpl.class);

    /**
     * For reflection purposes, i.e. Spring needs this.
     */
    public RoleDAOImpl()
    {
        super(Role.class);
    }

    public RoleDAOImpl(Session session)
    {
        super(Role.class, session);
    }


    protected void setSession(Session s)
    {
        session = s;
    }

    @Override
    public Role save( Role role )
    {
        throw new UnsupportedOperationException("Can not save a role - immutable type.");
    }

    @Override
    public void delete( Role role )
    {
        throw new UnsupportedOperationException("Can not delete a role - immutable type.");
    }


    @Override
    public void delete( Long role )
    {
        throw new UnsupportedOperationException("Can not delete a role - immutable type.");   
    }

}