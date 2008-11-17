package org.grouter.common.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Helper methods.
 *
 * @author Georges Polyzois
 */
public class DateUtil
{
    /**
     * Get date from ISO standar formatter.
     * @param str
     * @return
     */
    public static DateTime getFromString(final String str)
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dt = fmt.parseDateTime(str);
        return dt;
    }


}
