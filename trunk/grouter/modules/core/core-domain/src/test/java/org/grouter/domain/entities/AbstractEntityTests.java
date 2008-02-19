package org.grouter.domain.entities;

import junit.framework.TestCase;
import org.hibernate.validator.InvalidValue;

/**
 * @author Georges
 */
abstract class AbstractEntityTests extends TestCase
{

    /**
     * @param invalidValues
     * @return
     */
    protected boolean isEmpty(InvalidValue[] invalidValues)
    {
        for (InvalidValue invalidValue : invalidValues)
        {
            if (!invalidValue.getMessage().equalsIgnoreCase(""))
            {
                return false;
            }
        }
        return true;
    }

}
