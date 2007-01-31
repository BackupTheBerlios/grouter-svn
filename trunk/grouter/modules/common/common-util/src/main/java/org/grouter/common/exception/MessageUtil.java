package org.grouter.common.exception;

import java.util.ResourceBundle;
import java.util.Locale;
import java.text.MessageFormat;

/**
 * Use this to localize error messages in exceptions.
 *
 * <pre>
 * public class MyClass implements GrouterResourcesKeys
 * {
 *   ...
 *   if (!file.exists())
 *   {
 *     throw new ResourceException( MessageUtil.formatMessage(MSG_FILE_NOT_FOUND, file.getName()));
 *   }
 * }
 * </pre>
 *
 * @author Georges Polyzois
 */
public class MessageUtil
{
    private static ResourceBundle resourceBundle;

    private static String getMessageString(String messageKey, Locale locale)
    {
        resourceBundle = ResourceBundle.getBundle("org.grouter.common.exception.GrouterResources", locale);
        return resourceBundle.getString(messageKey);
    }

    public static String formatMessage(String messageKey, Locale locale)
    {
        MessageFormat mf = new MessageFormat(getMessageString(messageKey, locale));
        return mf.format(new Object[0]);
    }

    public static String formatMessage(String messageKey, Object messsage, Locale locale)
    {
        MessageFormat mf = new MessageFormat(getMessageString(messageKey, locale));
        Object[] args = new Object[1];
        args[0] = messsage;
        return mf.format(args);
    }

    public static String formatMessage(String messageKey, Object[] messages, Locale locale)
    {
        MessageFormat mf = new MessageFormat(getMessageString(messageKey, locale));
        return mf.format(messages);
    }
}
