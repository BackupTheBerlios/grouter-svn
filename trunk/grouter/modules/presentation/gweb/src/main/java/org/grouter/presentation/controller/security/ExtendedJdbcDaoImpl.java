package org.grouter.presentation.controller.security;


import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.jdbc.JdbcDaoImpl;

import org.springframework.dao.DataAccessException;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

import javax.sql.DataSource;


/**
 * We are using an extension point i Spring Security JdbcDaoImpl class to load data into Security context after
 * sucessful authenitcation.
 *
 * @author Georges Polyzois
 */
public class ExtendedJdbcDaoImpl extends JdbcDaoImpl
{
    @Override
    public UserDetails loadUserByUsername( String username )
            throws UsernameNotFoundException, DataAccessException
    {
        List users = ( new PersonsByUsernameMapping( getDataSource(  ) ) ).execute( username );

        if ( users.size(  ) == 0 )
        {
            throw new UsernameNotFoundException( "User not found" );
        }

        ExtendedUserDetailsImpl extendedUserDetailsImpl = ( ExtendedUserDetailsImpl ) users.get( 0 );

        List dbAuths = getAuthoritiesByUsernameMapping(  ).execute( extendedUserDetailsImpl.getUsername(  ) );

        if ( dbAuths.size(  ) == 0 )
        {
            throw new UsernameNotFoundException( "User has no GrantedAuthority" );
        }

        GrantedAuthority[] arrayAuths = {  };

        addCustomAuthorities( extendedUserDetailsImpl.getUsername(  ), dbAuths );

        arrayAuths = ( GrantedAuthority[] ) dbAuths.toArray( arrayAuths );

        ExtendedUserDetailsImpl userDetailsImpl = new ExtendedUserDetailsImpl( extendedUserDetailsImpl.getUsername(  ),
                extendedUserDetailsImpl.getPassword(  ), extendedUserDetailsImpl.isEnabled(  ),
                extendedUserDetailsImpl.isAccountNonExpired(  ), extendedUserDetailsImpl.isCredentialsNonExpired(  ),
                extendedUserDetailsImpl.isAccountNonLocked(  ), arrayAuths );

        userDetailsImpl.setModelFullName( extendedUserDetailsImpl.getModelFullName(  ) );

        return userDetailsImpl;
    }



    protected MappingSqlQuery getAuthoritiesByUsernameMapping(  )
    {
        return authoritiesByUsernameMapping;
    }

    /**
     * Query object to look up a user.
     */
    protected class PersonsByUsernameMapping extends MappingSqlQuery
    {
        protected PersonsByUsernameMapping( DataSource ds )
        {
            super( ds, getUsersByUsernameQuery(  ) );
            declareParameter( new SqlParameter( Types.VARCHAR ) );
            compile(  );
        }

        protected Object mapRow( ResultSet rs, int rowNum )
                throws SQLException
        {
            String username = rs.getString( 1 );
            String password = rs.getString( 2 );
            boolean enabled = rs.getBoolean( 3 );
            String modelUserName = rs.getString( 4 );
            String modelFullName = rs.getString( 5 );

            ExtendedUserDetailsImpl extendedUserDetails = new ExtendedUserDetailsImpl( username, password, enabled,
                    true, true, true, new GrantedAuthority[] { new GrantedAuthorityImpl( "HOLDER" ) } );
            extendedUserDetails.setModelFullName( modelFullName );
            extendedUserDetails.setModelUserName( modelUserName );

            return extendedUserDetails;
        }
    }
}

