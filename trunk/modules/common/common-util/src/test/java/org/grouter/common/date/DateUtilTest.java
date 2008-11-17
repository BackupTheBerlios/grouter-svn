package org.grouter.common.date;

import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Some tests
 *
 * @author Georges Polyzois
 */
public class DateUtilTest
{
    @Before
    public void setUp()
    {
        // Add your code here
    }

    @Test
    public void testGetFromString()
    {
        DateTime dateTime = DateUtil.getFromString("2008-02-01");
        assertEquals(2008, dateTime.getYear());
        assertEquals(02, dateTime.getMonthOfYear());
        assertEquals(01, dateTime.getDayOfMonth());

    }
}
