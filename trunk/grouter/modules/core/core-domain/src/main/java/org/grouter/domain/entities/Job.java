package org.grouter.domain.entities;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Georges Polyzois
 */
public class Job

{    List<JobEvent> schedulerJobEvents = new ArrayList<JobEvent>();

    JobState jobState;

    // on or off
    Boolean enabled;

    String jobName;

    String cronExpression;



    Map<String, String> jobContext = new HashMap<String,String>();


}
