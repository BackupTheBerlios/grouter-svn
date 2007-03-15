package org.grouter.presentation.controller.user;
import org.apache.commons.lang.Validate;

import org.apache.log4j.Logger;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.validation.BindException;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.grouter.domain.servicelayer.UserService;
import org.grouter.domain.entities.User;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles the edit form for a Node.
 *
 * @author Georges Polyzois
 */
public class UserEditController extends SimpleFormController
{
    private static Logger logger = Logger.getLogger( UserEditController.class );
    private final static String ID = "id";
    private static final String FORMVIEW = "user/editUser";
    private static final String SUCCESSVIEW = "user/list.do";

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    private UserService userService;


    /**
     * Prefered way of init these settings since they are static.
     */
    public UserEditController(  )
    {
        setSessionForm( true );
        setCommandClass( UserCommand.class );
        setFormView( FORMVIEW );
        setSuccessView( SUCCESSVIEW );
    }


    /**
     * Called on submit - stores a User.
     */
    @Override
    protected ModelAndView onSubmit( HttpServletRequest req, HttpServletResponse res, Object object, BindException bex )
            throws Exception
    {
        String message;
        UserCommand cmd = ( UserCommand ) object;


        User user = cmd.getUser(  );

        try
        {
            userService.saveUser( user );
            message = "Sparat";
        }
        catch ( Exception e )
        {
            logger.error( e, e );
            message = "Kunde inte spara";
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( "message", message );

        return showForm( req, res, bex, model );
        //return new ModelAndView( LIST_VIEW + "?listingDate=" + listingDate, model );
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected void initBinder( HttpServletRequest request, ServletRequestDataBinder binder )
            throws Exception
    {
        binder.registerCustomEditor( Date.class, "article.published",
            new CustomDateEditor( new SimpleDateFormat( "yyyy-MM-dd" ), true ) );
    }



    /**
     * Callback method for intializing command/form bean.
     */
    @Override
    protected Object formBackingObject( HttpServletRequest request )
            throws Exception
    {
        UserCommand cmd;
        Long id = getId( request, ID);

        if ( id != null )
        {
            User user = userService.findById( id );
            cmd = new UserCommand( user );
        }
        else
        {
            cmd = new UserCommand(  );
        }

        return cmd;
    }



    /**
     * Helper.
     * @param request  a HttpServletRequest
     * @param id   an id
     * @return an id
     */
    private Long getId( HttpServletRequest request, String id )
    {
        if ( ( request != null ) && ( request.getParameter( id ) != null ) )
        {
            try
            {
                return ServletRequestUtils.getLongParameter( request, id );
            }
            catch ( ServletRequestBindingException e )
            {
                logger.error( "Could not get id from request - probably not a valid Long", e );
            }
        }

        return null;
    }



    /**
     * Data needed by view.
     * <p/>
     * {@inheritDoc}
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    protected Map referenceData( HttpServletRequest request )
            throws Exception
    {
        Map<String, Object> model = new HashMap<String, Object>(  );

        List<User> users = userService.findAll();
        model.put( "users", users );

        return model;
    }

}