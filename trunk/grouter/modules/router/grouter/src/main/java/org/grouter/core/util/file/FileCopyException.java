package org.grouter.core.util.file;

import java.io.*;

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
