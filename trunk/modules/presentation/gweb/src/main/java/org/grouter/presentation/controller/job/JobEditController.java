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
import org.grouter.domain.entities.JobState;
import org.grouter.domain.entities.JobType;
import org.grouter.domain.entities.Router;
import org.grouter.domain.service.JobService;
import org.grouter.domain.service.RouterService;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Handles the edit form for a Job.
 *
 * @author Georges Polyzois
 */
public class JobEditController extends SimpleFormController {
    private static Logger logger = Logger.getLogger(JobEditController.class);
    private final static String ID = "id";
    private static final String FORMVIEW = "job/editjob";
    private static final String SUCCESSVIEW = "redirect:list.do";
    //  In <form:form commandName="loanInfo"
    private static final String JOBCOMMAND = "jobcommand";
    private JobEditCommandValidator jobEditValidator = new JobEditCommandValidator();


    private JobService jobService;
    private RouterService routerService;


    /**
     * Prefered way of init these settings since they are static.
     */
    public JobEditController() {

//        isValidateOnBinding();


        setSessionForm(true);
        setCommandClass(JobEditCommand.class);
        setFormView(FORMVIEW);
        setSuccessView(SUCCESSVIEW);
        setCommandName(JOBCOMMAND);
        setValidator(jobEditValidator);
    }

    protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
        Map model = errors.getModel();
        return super.onSubmit(command, errors);
    }



    

    /**
     * Called on submit - stores a User.
     */
    @Override
    /*protected ModelAndView onSubmit( HttpServletRequest req, HttpServletResponse res, Object object, BindException bex )
            throws Exception
    {
        String message;
        JobEditCommand cmd = (JobEditCommand) object;
        Job job = cmd.getJob();

        try
        {
            jobService.save( job );
            message = "Saved";
        }
        catch ( Exception e )
        {
            logger.error( e, e );
            message = "Could not save";
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( "message", message );

        //return showForm( req, res, bex, model );
        return new ModelAndView( SUCCESSVIEW, model );
    }
 */

    /**
     * Callback method for intializing command/form bean.
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        JobEditCommand cmd;
        String id = getId(request, ID);
        logger.debug("Get for id : " + id);

        if (id != null) {
            Job job = jobService.findById(id);
            cmd = new JobEditCommand(job);
        } else {
            cmd = new JobEditCommand();
        }

        return cmd;
    }

    /**
     * Helper.
     *
     * @param request a HttpServletRequest
     * @param id      an id
     * @return an id
     */
    private String getId(HttpServletRequest request, String id) {
        if ((request != null) && (request.getParameter(id) != null)) {
            try {
                return ServletRequestUtils.getStringParameter(request, id);
            }
            catch (ServletRequestBindingException e) {
                logger.error("Could not get id from request - probably not a valid Long", e);
            }
        }

        return null;
    }

    /**
     * Data needed by view.
     * {@inheritDoc}
     */
    @Override
    protected Map referenceData(HttpServletRequest request)
            throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<Job> jobs = jobService.findAll();
        model.put("jobs", jobs);
        List<Router> routers = routerService.findAll();
        model.put("routers", routers);
        model.put("jobStates", JobState.values());
        model.put("jobTypes", JobType.values());
        return model;
    }

    public void setJobService(final JobService jobService) {
        this.jobService = jobService;
    }

    public void setRouterService(final RouterService routerService) {
        this.routerService = routerService;
    }
}