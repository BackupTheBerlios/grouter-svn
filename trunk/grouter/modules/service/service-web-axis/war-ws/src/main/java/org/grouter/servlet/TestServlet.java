package org.grouter.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.grouter.ws.grouterservice.GRouterService;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-feb-20
 * Time: 16:19:25
 * To change this template use File | Settings | File Templates.
 */

public class TestServlet extends javax.servlet.http.HttpServlet
{
    private static Log log = LogFactory.getLog(GRouterService.class);

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        System.out.println("Hey, browser!");
        response.setContentType("text/plain");

        // this line will be seen in the browser
        System.out.println("Hey, browser!");

        // this line goes to error logs or such
        System.err.println("Hey, error log!");
        log.debug("Logging ###########");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException
    {
        doPost(request,response);

    }
}
