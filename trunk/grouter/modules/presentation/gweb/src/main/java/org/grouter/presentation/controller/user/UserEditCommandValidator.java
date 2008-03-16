package org.grouter.presentation.controller.user;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.grouter.domain.service.UserService;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.EntityValidator;
import org.hibernate.validator.InvalidValue;
import org.apache.commons.lang.Validate;
import org.apache.commons.validator.EmailValidator;

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
        //ValidationUtils.rejectIfEmpty(errors, "user.roles", "userEditCommand.user.firstName", "Firstname is required.");
        ValidationUtils.rejectIfEmpty(errors, "user.address.email", "userEditCommand.user.address.email", "Email is required.");

        if( !EmailValidator.getInstance().isValid( userEditCommand.getUser().getAddress().getEmail() ) )
        {
            errors.rejectValue( "user.address.email", "userEditCommand.user.address.emailInvalid", "Email is invalid.");
        }


    }
}