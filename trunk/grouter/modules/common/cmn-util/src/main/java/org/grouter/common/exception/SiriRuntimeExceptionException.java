package org.grouter.common.exception;

/**
 * General unchecked exception used for communcation of all technical
 * problems within found in the source code.
 */
public class SiriRuntimeExceptionException
        extends RuntimeException
{
    /**
     * Constant representing the severity level ERROR. This means
     * the current thread is aborted, but there is no problem to
     * continue running other threads in the application. This severity
     * level corresponds to the log level with the same name in Log4j.
     */
    public static final int ERROR = 1;

    /**
     * Constant representing the severity level FATAL. This means
     * the current thread is aborted, and the top-level exception handler
     * is <i>recommended to</i> abort the entire process. The reason for the
     * recommendation is that application code sometimes, for example in a
     * J2EE container, does not have the mandate to abort the entire JVM. This
     * severity level corresponds to the log level with the same name in Log4j.
     */
    public static final int FATAL = 2;

    private int severitylevel;

    /**
     * New exception with the default severity level {@link #ERROR}.
     *
     * @param message String is a text description of the reason for the error.
     */
    public SiriRuntimeExceptionException(String message)
    {
        super(message);
        severitylevel = ERROR;
    }

    /**
     * New exception with severity level defined.
     *
     * @param message       String is a text description of the reason for the error.
     * @param severityLevel is either {@link #ERROR} or {@link #FATAL}.
     */
    public SiriRuntimeExceptionException(String message, int severityLevel)
    {
        super(message);
        this.severitylevel = severityLevel;
    }

    /**
     * New exception with the default severity level {@link #ERROR}, and a
     * root cause.
     *
     * @param message   String is a text description of the reason for the error.
     * @param rootcause is the root cause for this exception.
     */
    public SiriRuntimeExceptionException(String message, Throwable rootcause)
    {
        super(message, rootcause);
        severitylevel = ERROR;
    }

    /**
     * New exception with severity level defined.
     *
     * @param message       String is a text description of the reason for the error.
     * @param rootcause     is the root cause for this exception.
     * @param severityLevel is either {@link #ERROR} or {@link #FATAL}.
     */
    public SiriRuntimeExceptionException(String message, Throwable rootCause, int severityLevel)
    {
        super(message, rootCause);
        this.severitylevel = severityLevel;
    }

    /**
     * Get the severity level associated with this exception.
     *
     * @return either {@link #ERROR} or {@link #FATAL}.
     */
    public int getSeverityLevel()
    {
        return severitylevel;
    }
}
