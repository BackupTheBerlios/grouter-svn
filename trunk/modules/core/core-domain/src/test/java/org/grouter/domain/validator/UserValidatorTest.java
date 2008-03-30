package org.grouter.domain.validator;

import org.grouter.domain.entities.User;
import org.springframework.validation.Validator;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import junit.framework.TestCase;

/**
 *
 * @author Georges Polyzois
 */
public class UserValidatorTest extends TestCase
{
    public void testEmptyPersonValidation() throws Exception
    {
        User user = new User();
        Validator validator = new UserValidator();
        BindException errors = new BindException(user, "user");
        validator.validate(user, errors);


        assertEquals("userForm.password.error", errors.getFieldError("password").getCode());
        assertEquals("userForm.userName.error", errors.getFieldError("userName").getCode());

    }
}

