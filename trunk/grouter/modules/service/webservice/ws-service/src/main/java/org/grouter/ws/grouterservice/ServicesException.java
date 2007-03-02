package org.grouter.ws.grouterservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Geoges Polyzois
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
