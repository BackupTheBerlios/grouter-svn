package org.grouter.common.exception;

import java.util.ListResourceBundle;

/**
 *
 * 
 * @author Georges
 */
public class GrouterResources_en_US extends ListResourceBundle implements MessageKeys
{
    public Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents =
            {
                    // Localize from here
                    {MSG_FILE_NOT_FOUND, "Cannot find file {0}"},
                    {MSG_CANT_OPEN_FILE, "Cannot open file {0}"},
                    {REMOTE_EXCEPTION_MESSAGE, "Got a remote exception {0}"},

                    // Localize to here
            };
}

