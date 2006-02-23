package org.grouter.ws.grouterservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: georges.polyzois
 * Date: 2006-feb-15
 * Time: 08:30:57
 * To change this template use File | Settings | File Templates.
 */
public class ServicesException extends Exception
{
    String errorCode;

    public ServicesException(String message, String errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }
}
