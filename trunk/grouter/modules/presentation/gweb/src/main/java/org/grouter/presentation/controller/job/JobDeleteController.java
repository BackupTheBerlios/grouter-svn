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
import org.grouter.domain.servicelayer.JobService;
import org.grouter.presentation.controller.AbstractRouterController;
import org.grouter.presentation.controller.user.UserListController;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * A controller for Message listing.
 *
 * @author Georges Polyzois
 */
class JobDeleteController extends AbstractRouterController
{
    private static Logger logger = Logger.getLogger(UserListController.class);
    private JobService jobService;
    protected static final String LIST_VIEW = "redirect:/job/list.do?";




    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Long id = getId(request, ID);
        logger.debug("Got request to delete job with id : " + id);

                      

        if (id != null)
        {
            jobService.delete( id );
            map.put(MESSAGE, "message=" + "Job was deleted" + "(id=" + id + ")" );
        }


        return new ModelAndView(LIST_VIEW , map);
    }


    /**
     * Helper.
     *
     * @param request a HttpServletRequest
     * @param id  an id
     * @return an id
     */
    private Long getId(HttpServletRequest request, String id)
    {
        if ((request != null) && (request.getParameter(id) != null))
        {
            try
            {
                return ServletRequestUtils.getLongParameter(request, id);
            }
            catch (ServletRequestBindingException e)
            {
                logger.error("Could not get id from request - probably not a valid Long", e);
            }
        }

        return null;
    }



    public void setJobService(final JobService jobService)
    {
        this.jobService = jobService;
    }
}