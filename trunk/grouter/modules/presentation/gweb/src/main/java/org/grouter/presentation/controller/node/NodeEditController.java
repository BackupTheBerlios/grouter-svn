package org.grouter.presentation.controller.node;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.validation.BindException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.UserService;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.Node;
import org.grouter.presentation.controller.user.UserCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Handles the edit form for a Node.
 *
 * @author Georges Polyzois
 */
public class NodeEditController extends SimpleFormController
{
    private static Logger logger = Logger.getLogger( NodeEditController.class );
    private final static String ID = "id";
    private static final String FORMVIEW = "node/editNode";
    private static final String SUCCESSVIEW = "node/list.do";
    private RouterService routerService;



    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }

    /**
     * Prefered way of init these settings since they are static.
     */
    public NodeEditController(  )
    {
        setSessionForm( true );
        setCommandClass( NodeCommand.class );
        setFormView(FORMVIEW);
        setSuccessView(SUCCESSVIEW);
    }


    /**
     * Called on submit - stores a User.
     */
    @Override
    protected ModelAndView onSubmit( HttpServletRequest req, HttpServletResponse res, Object object, BindException bex )
            throws Exception
    {
        String message;
        NodeCommand cmd = ( NodeCommand ) object;


        Node node = cmd.getNode();

        try
        {
            routerService.saveNode( node );
            message = "Saved";
        }
        catch ( Exception e )
        {
            logger.error( e, e );
            message = "Could no save";
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( "message", message );

        return showForm( req, res, bex, model );
        //return new ModelAndView( LIST_VIEW + "?listingDate=" + listingDate, model );
    }


    /**
     * Callback method for intializing command/form bean.
     */
    @Override
    protected Object formBackingObject( HttpServletRequest request )
            throws Exception
    {
        NodeCommand cmd;
        String id = ServletRequestUtils.getStringParameter( request, ID, null );
        logger.debug("GET -> id : " + id);

        if ( id != null )
        {
            Node node = routerService.findById( id );
            cmd = new NodeCommand( node );
        }
        else
        {
            cmd = new NodeCommand(  );
        }

        return cmd;
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


        return model;
    }

}
