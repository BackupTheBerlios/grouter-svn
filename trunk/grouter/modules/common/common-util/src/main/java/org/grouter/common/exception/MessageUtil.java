/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
