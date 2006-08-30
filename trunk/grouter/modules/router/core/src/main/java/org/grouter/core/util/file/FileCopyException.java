package org.grouter.core.util.file;

import java.io.IOException;

/**
 * Exception raised whe copying file fails.
 */
class FileCopyException
        extends IOException
{
    public FileCopyException(String msg)
    {
        super(msg);
    }
}
