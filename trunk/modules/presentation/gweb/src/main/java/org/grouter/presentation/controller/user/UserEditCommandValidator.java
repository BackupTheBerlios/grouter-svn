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
package org.grouter.presentation.controller.user;

import org.apache.commons.validator.EmailValidator;
import org.grouter.domain.service.UserService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates the user form.
 *
 *
 * @author Georges Polyzois
 *
 */
public class UserEditCommandValidator implements Validator
{
    private UserService userService;

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    public boolean supports(Class clazz)
    {
        return UserEditCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors)
    {
        UserEditCommand userEditCommand = (UserEditCommand) object;

        // All errors are specified in message property files
        ValidationUtils.rejectIfEmpty(errors, "user.userName", "userEditCommand.user.userName", "Username is required.");
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

        }
    }
}