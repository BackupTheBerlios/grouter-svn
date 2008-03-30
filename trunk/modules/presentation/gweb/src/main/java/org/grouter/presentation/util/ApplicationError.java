package org.grouter.presentation.util;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

//todo doc this
public class ApplicationError extends RuntimeException
{
    /**
     * key to error text found in messages.properties file
     */
    private String errorProperty;

    /**
     * Object array that will be used if there are any variables in the error text
     */
    private Object[] values;

    /**
     * If the errorProperty key is not found in messages.properties this default value will be used as the error text instead
     */
    private String defaultMessage;

    public String getErrorMessage(int index, javax.servlet.http.HttpServletRequest request)
    {
        ResourceBundleMessageSource r = new ResourceBundleMessageSource();
        r.setBasename("messages");
        Locale locale = RequestContextUtils.getLocale(request);
        return r.getMessage(errorProperty, values, defaultMessage, locale);
    }
}
