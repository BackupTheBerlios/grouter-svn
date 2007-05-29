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
public class NodeTest extends AbstractEntityTests
{
    private static Logger logger = Logger.getLogger(NodeTest.class);

    /*
    public void testValidateInValid()
    {
        Node node = new Node();
 TODO        InvalidValue[] invalidValues = EntityValidator.validate(node);

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
        Node node = new Node("id","name");
        InvalidValue[] invalidValues = EntityValidator.validate(node);

        assertTrue("Errors hen validating entity :" + ToStringBuilder.reflectionToString(invalidValues) , isEmpty(invalidValues) );
    }
  */

}