/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.presentation.controller.router;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Router;
import org.grouter.domain.service.RouterService;
import org.grouter.presentation.controller.job.JobEditCommand;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Handles the edit form for a Router.
 *
 * @author Georges Polyzois
 */
public class RouterEditController extends SimpleFormController
{
    private static Logger logger = Logger.getLogger( RouterEditController.class );
    private final static String ID = "id";
    private static final String FORMVIEW = "router/editrouter";
    private static final String SUCCESSVIEW = "redirect:list.do";
    private static final String ROUTER = "routercommand";
    private RouterService routerService;

    /**
     * Prefered way of init these settings since they are static.
     */
    public RouterEditController(  )
    {
        setSessionForm( true );
        setCommandClass( JobEditCommand.class );
        setFormView( FORMVIEW );
        setSuccessView( SUCCESSVIEW );
        setCommandName(ROUTER);
        //setValidator();
    }


    /**
     * Called on submit - stores a User.
     */
    @Override
    protected ModelAndView onSubmit( HttpServletRequest req, HttpServletResponse res, Object object, BindException bex )
            throws Exception
    {
        String message;
        RouterCommand cmd = ( RouterCommand ) object;

        Router router = cmd.getRouter();



        try
        {
            routerService.saveRouter( router );
            message = "Saved";
        }
        catch ( Exception e )
        {
            logger.error( e, e );
            message = "Could not save";
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( "message", message );

        //return showForm( req, res, bex, model );
        return new ModelAndView( SUCCESSVIEW, model );
    }





    /**
     * Callback method for intializing command/form bean.
     */
    @Override
    protected Object formBackingObject( HttpServletRequest request )
            throws Exception
    {
        RouterCommand cmd = null;
        String id = ServletRequestUtils.getStringParameter( request, ID);
        logger.debug("Get for id : " + id);

        /*
        if ( id != null )
        {
            Router router = routerService.findRouterById( id );
            cmd = new RouterCommand( router );
        }
        else
        {
            cmd = new RouterCommand(  );
        }       */

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
     * {@inheritDoc}
     */
    @Override
    protected Map referenceData( HttpServletRequest request )
            throws Exception
    {
        Map<String, Object> model = new HashMap<String, Object>(  );


        List<Router> routers = routerService.findAll();
        model.put( "routers", routers );


        return model;
    }


    public void setRouterService(final RouterService routerService)
    {
        this.routerService = routerService;
    }
}