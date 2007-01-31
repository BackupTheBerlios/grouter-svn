package org.grouter.common.exception;

import java.util.ListResourceBundle;

/**
 *
 *
 * @author Georges
 */
public class GrouterResources_sv_SE extends ListResourceBundle implements MessageKeys
{
    public Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents =
            {
                    // Localize from here
                    {MSG_FILE_NOT_FOUND, "Kunde inte hitta filen{0}"},
                    {MSG_CANT_OPEN_FILE, "Kunde inte öppna filen {0}"},
                    {REMOTE_EXCEPTION_MESSAGE, "Fick ett excpetion {0}"},

                    // Localize to here
            };
}
