package org.grouter.presentation.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The default implementation of the interface HandlerExceptionResolver is the
 * {@link SimpleMappingExceptionResolver}.
 * This class maps exceptions to view names by the exception class name or a substring of the class name.
 * This implementation can be configured for individual Controllers or for globally for all handlers.
 *
 * This class overrides {@link SimpleMappingExceptionResolver} to store localized messages in the model for
 * the view to render.
 *
 * 
 *
 * @author Georges Polyzois
 */


public class ApplicationMappingExceptionResolver extends SimpleMappingExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map myModel = new HashMap();
        if (ex instanceof ApplicationError) {
            ApplicationError x = (ApplicationError) ex;
            int size = 1; //x.getErrorMsgSize();
            List messages = new ArrayList();
            for (int i = 0; i < size; i++)
            {
                messages.add(x.getErrorMessage(i, request));
            }
            myModel.put("messages", messages);
            return new ModelAndView("Message", "model", myModel);
        }
        else
        {
            myModel.put("messages", ex.getMessage());
        }
        //super.resolveException(request,response,handler,ex);

        return new ModelAndView(determineViewName(ex, request), myModel);
    }

}
