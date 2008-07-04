package org.grouter.presentation.controller.node;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.grouter.domain.service.UserService;
import org.grouter.domain.service.RouterService;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.EntityValidator;
import org.grouter.presentation.controller.user.UserEditCommand;
import org.hibernate.validator.InvalidValue;
import org.apache.commons.lang.Validate;
import org.apache.commons.validator.EmailValidator;

/**
 * Validates the user form.
 *
 * @author Georges Polyzois
 */
public class NodeEditCommandValidator implements Validator
{
    private RouterService routerService;


    public void setRouterService(RouterService routerService)
    {
        this.routerService = routerService;
    }


    public boolean supports(Class clazz)
    {
        return NodeEditCommand.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors)
    {
        NodeEditCommand nodeEditCommand = (NodeEditCommand) object;

        // All errors are specified in message property files
        ValidationUtils.rejectIfEmpty(errors, "node.displayName", "nodeEditCommand.node.displayName", "Displayname is required.");

        /*if (!EmailValidator.getInstance().isValid(userEditCommand.getUser().getAddress().getEmail()))
        {
            errors.rejectValue("user.address.email", "userEditCommand.user.address.emailInvalid", "Email is invalid.");
        } */
    }
}