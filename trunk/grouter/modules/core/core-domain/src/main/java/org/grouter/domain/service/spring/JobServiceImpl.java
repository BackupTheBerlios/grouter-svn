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

package org.grouter.domain.service.spring;

import org.grouter.domain.dao.JobDAO;
import org.grouter.domain.entities.Job;
import org.grouter.domain.entities.JobState;
import org.grouter.domain.service.JobService;

import java.util.List;

/**
 * Handles operations on Users and user roles.
 *
 * @author Georges Polyzois
 */
public class JobServiceImpl implements JobService
{
    JobDAO jobDAO;

    /**
     * Injected.
     *
     * @param jobDAO injected DAO
     */
    public void setJobDAO(JobDAO jobDAO)
    {
        this.jobDAO = jobDAO;
    }

    public List<Job> findAll()
    {
        return jobDAO.findAll();
    }

    public List<Job> searchJobs(String searchText)
    {
        return jobDAO.findFromIndex(searchText, "displayName");
    }

    public void save(final Job job)
    {
        if (job.getJobState() == null)
        {
            job.setJobState(JobState.STOPPED);
        }
        jobDAO.save(job);
    }


    public Job findById(Long id)
    {
        return jobDAO.findById(id);
    }

    public void delete(final Long id)
    {
        jobDAO.delete(id);
    }

}