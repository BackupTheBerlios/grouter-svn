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
package org.grouter.presentation.controller.message;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates the user form.
 *
 *
 * @author Georges Polyzois
 *
 */
public class MessageSearchCommandValidator implements Validator
{
    public boolean supports(Class clazz)
    {
        return MessageSearchCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors)
    {
        MessageSearchCommand messageSearchCommand = (MessageSearchCommand) object;

        // All errors are specified in message property files
      /*  if( messageSearchCommand.getFromDate() != null )
        {
            try
            {
                DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
                //fmt.parseDateTime( messageSearchCommand.getFromDate().toString() );
            } catch (IllegalArgumentException e)
            {
                // illegal format of date string
                errors.rejectValue( "fromDate", "messageSearchCommand.fromDate",
                                    "Wrong format of date field, use [YYYY-MM-DD].");                
            }
        }

        if( messageSearchCommand.getToDate() != null )
        {
            try
            {
                DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
                //fmt.parseDateTime( messageSearchCommand.getToDate() );
            } catch (IllegalArgumentException e)
            {
                // illegal format of date string
                errors.rejectValue( "fromDate", "messageSearchCommand.toDate",
                                    "Wrong format of date field, use [YYYY-MM-DD].");
            }
        }  */


        /*
        ValidationUtils.rejectIfEmpty(errors, "user.password", "userEditCommand.user.password", "Password is required.");
        ValidationUtils.rejectIfEmpty(errors, "user.firstName", "userEditCommand.user.firstName", "Firstname is required.");
        ValidationUtils.rejectIfEmpty(errors, "user.userRoles", "userEditCommand.user.userRoles", "A user must have at least one role.");
        ValidationUtils.rejectIfEmpty(errors, "user.address.email", "userEditCommand.user.address.email", "Email is required.");

        if( !EmailValidator.getInstance().isValid( userEditCommand.getUser().getAddress().getEmail() ) )
        {
            errors.rejectValue( "user.address.email", "userEditCommand.user.address.emailInvalid", "Email is invalid.");
        }

        if(  userEditCommand.getUser().getUserRoles()  == null || userEditCommand.getUser().getUserRoles().size() == 0  )
        {
            errors.rejectValue( "user.userRoles", "userEditCommand.user.userRoles", "Email is invalid.");

        } */
    }
}