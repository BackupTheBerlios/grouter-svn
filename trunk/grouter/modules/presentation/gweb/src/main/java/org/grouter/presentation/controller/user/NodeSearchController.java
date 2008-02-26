package org.grouter.presentation.controller.user;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.grouter.domain.entities.User;
import org.grouter.domain.service.UserService;
import org.grouter.presentation.controller.message.MessageListController;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Jan 19, 2008
 * Time: 5:30:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeSearchController extends AbstractController
{
    private static Logger logger = Logger.getLogger(MessageListController.class);
    private static final String LIST_VIEW = "user/searchusers";

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        String searchText = ServletRequestUtils.getStringParameter(request, "searchText", null);

        if (StringUtils.isNotEmpty(searchText)) {
            List<User> users = userService.searchUsers(searchText);
            map.put("users", users);
        }

        return new ModelAndView(LIST_VIEW, map);
    }

}