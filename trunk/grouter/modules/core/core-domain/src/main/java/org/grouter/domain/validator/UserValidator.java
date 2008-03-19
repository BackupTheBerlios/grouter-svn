package org.grouter.domain.validator;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.grouter.domain.service.UserService;
import org.grouter.domain.entities.User;
import org.grouter.domain.entities.EntityValidator;
import org.hibernate.validator.InvalidValue;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 */
public class UserValidator implements Validator
{
    private UserService userService;

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    public boolean supports(Class clazz)
    {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object object, Errors errors)
    {
        User user = (User) object;

        InvalidValue[] invalids = EntityValidator.validate(user);

        // Perform "expensive" validation only if no simple errors found above.
        /*   if (   invalids == null || invalids.length == 0)
        {
            boolean validUser = userService.validateExpensive(user);
            if (!validCard
               )
            {
                errors.reject("error.user.invalid");
            }
        } else
        */
        for (InvalidValue invalidValue : invalids)
        {
            errors.rejectValue( invalidValue.getPropertyPath(),"userForm." + invalidValue.getPropertyPath() + ".error", null, invalidValue.getMessage());
        }

     //   ValidationUtils.rejectIfEmpty(         errors, "vehicle.state", "required.state","State is required.");



    }
}