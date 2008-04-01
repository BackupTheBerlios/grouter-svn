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

package org.grouter.presentation.controller.node;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Node;
import org.grouter.domain.service.RouterService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    private static final String NODECOMMAND = "nodecommand";


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
        setCommandName(NODECOMMAND);
    }


    /**
     * Called on submit - stores a Node.
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
            Node node = routerService.findNodeById( Long.parseLong(id) );
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
     * 
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
