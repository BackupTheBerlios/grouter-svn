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

package org.grouter.presentation.controller.job;

import org.apache.log4j.Logger;
import org.grouter.domain.entities.Job;
import org.grouter.domain.servicelayer.JobService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A controller for Job listing.
 *
 * @author Georges Polyzois
 */
public class JobListController extends AbstractController
{
    private static Logger logger = Logger.getLogger(JobListController.class);
    private static final String LIST_VIEW = "job/listjobs";

    private JobService jobService;

    public void setJobService(final JobService jobService)
    {
        this.jobService = jobService;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response
    )
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Job> jobs = jobService.findAll();
        logger.debug("Finder returned number of jobs :" + jobs.size());
        map.put("jobs", jobs);
        map.put("jobsSize", jobs.size());
        return new ModelAndView(LIST_VIEW, map);
    }
}