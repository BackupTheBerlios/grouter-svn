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

import org.grouter.domain.service.JobService;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by IntelliJ IDEA.
 * User: georgespolyzois
 * Date: Sep 5, 2008
 * Time: 9:33:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class JobEditCommandValidator implements Validator {



    private JobService jobService;

    public void setUserService(JobService jobService)
    {
        this.jobService = jobService;
    }

    public boolean supports(Class clazz)
    {
        return JobEditCommandValidator.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors)
    {
        JobEditCommand validator = (JobEditCommand) object;

        // All errors are specified in message property files
     //   ValidationUtils.rejectIfEmpty(errors, "user.userName", "userEditCommand.user.userName", "Username is required.");
     //   ValidationUtils.rejectIfEmpty(errors, "user.password", "userEditCommand.user.password", "Password is required.");


    }
}
