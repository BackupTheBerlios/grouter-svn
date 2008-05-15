package org.grouter.presentation.controller.message;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.entities.Message;
import org.grouter.domain.service.RouterService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageSearchController extends AbstractController
{
    private static Logger logger = Logger.getLogger(MessageListController.class);
    private static final String LIST_VIEW = "message/listmessages";

    private RouterService routerService;


    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        String searchText = ServletRequestUtils.getStringParameter(request, "searchText", null);

        if( StringUtils.isNotEmpty( searchText ) )
        {
            List<Message> messages = routerService.searchMessages( searchText );
            map.put("messages", messages);
        }

        return new ModelAndView(LIST_VIEW, map);
    }


}
