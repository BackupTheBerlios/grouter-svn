package org.grouter.presentation.controller.user;

import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.servicelayer.UserService;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.Message;
import org.grouter.domain.entities.Node;
import org.grouter.domain.entities.User;

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
public class UserDeleteController extends AbstractController
{
    private static Logger logger = Logger.getLogger(UserListController.class);
    private static final String LIST_VIEW = "user/listusers";
    private UserService userService;
    private final static String ID = "id";


    /**
     * Injected.
     *
     * @param userService the service
     */
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Long id = getId(request, ID);
        logger.debug("Get for id : " + id);

        if (id != null)
        {
            User user = userService.findById(id);
        }
        return new ModelAndView(LIST_VIEW, map);
    }


    /**
     * Helper.
     *
     * @param request a HttpServletRequest
     * @param id      an id
     * @return an id
     */
    private Long getId(HttpServletRequest request, String id)
    {
        if ((request != null) && (request.getParameter(id) != null))
        {
            try
            {
                return ServletRequestUtils.getLongParameter(request, id);
            }
            catch (ServletRequestBindingException e)
            {
                logger.error("Could not get id from request - probably not a valid Long", e);
            }
        }

        return null;
    }
}