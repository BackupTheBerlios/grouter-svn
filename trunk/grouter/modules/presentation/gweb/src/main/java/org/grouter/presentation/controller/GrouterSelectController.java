package org.grouter.presentation.controller;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.GRouterService;
import org.grouter.domain.entities.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * A controller for Message listing.
 *
 * @author Georges Polyzois
 */
public class GrouterSelectController extends AbstractController
{
    private static Logger logger = Logger.getLogger(GrouterSelectController.class);
    private static final String LIST_VIEW = "message/listmessages";

    GRouterService gRouterService;



    public void setGRouterService(GRouterService gRouterService)
    {
        this.gRouterService = gRouterService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String routerId = ServletRequestUtils.getStringParameter(request, "routerid", null);

        if (routerId != null)
        {
            List<Node> nodes = gRouterService.findAllNodes(routerId);
            map.put("nodes", nodes);
            map.put("nodesSize", nodes.size());
            map.put("selectedGrouter", routerId );


            request.getSession().setAttribute( "selectedGrouter" , routerId);

        }


        return new ModelAndView(LIST_VIEW, map);
    }


}
