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

import org.grouter.domain.entities.Job;
import org.grouter.domain.entities.Router;
import org.grouter.domain.entities.JobType;

/**
 * 
 *
 *
 * @author Georges Polyzois
 */
public class JobEditCommand
{
    private Job job = new Job();

    public JobEditCommand(Job job)
    {
        this.job = job;
    }

    public JobEditCommand()
    {
        Router router = new Router();
        job.setRouter( router );

        if(job.getJobType() == null )
        {
            // To avoid null property when Spring walks the path in spring:bind and the User did not have any address
            // When spring loads an empty command - i.e. when we want to create a new one - this can not be null
            job.setJobType( JobType.SYNCHRONOUS );
        }

    }

    public Job getJob()
    {
        return job;
    }

    public void setJob(final Job job)
    {
        this.job = job;
    }
}