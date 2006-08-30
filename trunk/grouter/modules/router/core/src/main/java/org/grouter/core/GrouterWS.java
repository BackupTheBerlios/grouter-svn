package org.grouter.core;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-aug-28
 * Time: 14:18:43
 * To change this template use File | Settings | File Templates.
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class GrouterWS
{


    public void start()
    {
        System.out.println("##################");

    }
}
