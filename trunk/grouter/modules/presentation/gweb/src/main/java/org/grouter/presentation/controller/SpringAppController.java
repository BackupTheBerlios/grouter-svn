package org.grouter.presentation.controller;


import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.apache.log4j.Logger;
import org.grouter.domain.servicelayer.RouterService;
import org.grouter.domain.entities.Message;
import org.grouter.common.jndi.GlobalBeanLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SpringAppController implements Controller, ApplicationContextAware
{
    public void setGRouterService(RouterService gRouterService)
    {
        this.gRouterService = gRouterService;
    }

    public org.grouter.domain.servicelayer.RouterService gRouterService;
    protected static BeanFactory factory;
    private final static String beanName = "messageServiceManager";


    private static Logger logger = Logger.getLogger(SpringAppController.class);

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        logger.info("SpringappController - returning hello view");

        WebApplicationContext webApplicationContext = (WebApplicationContext) GlobalBeanLocator.getInstance().getApplicationContext();
        RouterService gRouterServiceService = (RouterService) webApplicationContext.getBean(beanName);

        Message message = gRouterServiceService.findMessageById("MESSAGE1");
        logger.info("grouterServiceImpl...." + message.getContent());
        return new ModelAndView("message.jsp", "message", message);
    }



    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

        logger.debug("Setting context for use in our dao factory");
         GlobalBeanLocator.getInstance().setApplicationContext(applicationContext);
    }
}


