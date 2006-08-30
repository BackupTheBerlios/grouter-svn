package org.grouter.core;

import javax.xml.ws.Endpoint;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-28
 * Time: 14:20:36
 * To change this template use File | Settings | File Templates.
 */

public class HttpServer
{


    static
    {
        Endpoint.publish("http://localhost:8888/ws-grouter/test", new GrouterWS());
    }

    public static void main(String[] args)
    {
        new HttpServer();
    }


}
