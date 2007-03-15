package org.grouter.presentation.controller.user;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Message;
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
public class UserListController extends AbstractController
{
    private static Logger logger = Logger.getLogger(UserListController.class);
    private static final String LIST_VIEW = "user/listusers";

    RouterService routerService;


    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();

        String nodeId = ServletRequestUtils.getStringParameter(request, "nodeid", null);
        String routerId = ServletRequestUtils.getStringParameter(request, "routerid", null);

        List<Router> routers = routerService.findAll();
        map.put("routers", routers);


        if (nodeId != null)
        {
            List<Message> messages = routerService.findAllMessages(nodeId);
            map.put("messages", messages);
            map.put("messagesSize", messages.size());
            map.put("selectedNodeId", nodeId );

        }

        if ( routerId != null)
        {
            List<Node> nodes = routerService.findAllNodes( routerId );
            map.put("nodes", nodes);
            map.put("selectedRouterId", routerId );
        }

        return new ModelAndView(LIST_VIEW, map);
    }


}
