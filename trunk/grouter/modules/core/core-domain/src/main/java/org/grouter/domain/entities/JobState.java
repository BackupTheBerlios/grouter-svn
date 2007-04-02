package org.grouter.domain.entities;

/**
 * @author Georges Polyzois
 */
public class JobState
{
    public static final JobState UNKNOWN = new JobState(1L, "UNKNOWN");
    public static final JobState PENDING = new JobState(2L, "PENDING");
    public static final JobState PROCESSING = new JobState(3L, "PROCESSING");
    public static final JobState CANCELED = new JobState(4L, "CANCELED");
    public static final JobState ABORTED = new JobState(5L, "ABORTED");
    public static final JobState COMPLETED = new JobState(6L, "COMPLETED");
    public static final JobState FAILED = new JobState(7L, "FAILED");

    public JobState(long id, String name)
    {

    }
}
