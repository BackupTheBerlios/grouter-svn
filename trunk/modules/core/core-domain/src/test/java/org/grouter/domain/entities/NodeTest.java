package org.grouter.domain.entities;

import org.apache.log4j.Logger;


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
        Node node = new Node("id","message");
        InvalidValue[] invalidValues = EntityValidator.validate(node);

        assertTrue("Errors hen validating entity :" + ToStringBuilder.reflectionToString(invalidValues) , isEmpty(invalidValues) );
    }
  */

}