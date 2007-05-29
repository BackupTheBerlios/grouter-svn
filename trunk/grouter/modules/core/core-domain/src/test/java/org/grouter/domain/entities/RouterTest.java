package org.grouter.domain.entities;

import org.hibernate.validator.InvalidValue;
import org.apache.log4j.Logger;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Test constraints on domain entity.
 *
 * @author Georges Polyzois
 */
public class RouterTest extends AbstractEntityTests
{
    private static Logger logger = Logger.getLogger(RouterTest.class);

    /*
    public void testValidateInValid()
    {
        Router router = new Router();
        InvalidValue[] invalidValues = EntityValidator.validate(router);

        assertNotNull(invalidValues);

        try
        {
            Validate.noNullElements(invalidValues);
        } catch (Exception e)
        {
            fail();
        }

        for (InvalidValue invalidValue : invalidValues)
        {
            logger.info(invalidValue);
        }
    }

    public void testValidateValid()
    {
        Router router = new Router("id","name");
        InvalidValue[] invalidValues = EntityValidator.validate(router);

        assertTrue("Errors hen validating entity :" + ToStringBuilder.reflectionToString(invalidValues) , isEmpty(invalidValues) );
    }

    */


}
