package org.grouter.common.hibernate;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * Utility methods.
 *
 * @author Georges Polyzois
 */
public class HibernateHelper
{
  
    private static Logger logger = Logger.getLogger(HibernateHelper.class);

    /**
     * Use this method topy all attributes from one model object to another
     * (Java Bean) - remember that all fields are copied including null fields.
     * Field names must match in both java beans and should be of same type.
     *
     * Copies collections, primitive types and other non primitive types - and
     * of course any null fields.
     *
     * @param from Object
     * @param to Object
     */
    public static void updateAllFields(Object from , Object to)
    {
        try
        {
            BeanUtils.copyProperties(to, from);
        }
        catch (Exception ex)
        {
            logger.warn("Failed updating from Object " , ex);
        }
    }

    /**
     * Use this method topy all attributes from one model object to another  -
     * this method removes null fields from originating from instance to preserve
     * existing fields in the to instance.
     * Field names must match in both java beans and should be of same type. Beware
     * that Maps are not copied to destination
     *
     * Note: BeanUtils.copyProperties(to, from) can not be used du to overwriting
     * values with nulls
     *
     * @param from Object
     * @param to Object
     */
    public static void updateNonNullFields(Object from , Object to)
    {
        try
        {
            Map fromMap = BeanUtils.describe(from);
            Set keySet = fromMap.keySet();
            Iterator iterKeys = keySet.iterator();
            while (iterKeys.hasNext())
            {
                Object key = iterKeys.next();
                Object value = fromMap.get(key);
                if (value != null)
                {
                    try
                    {
                        BeanUtils.setProperty(to, (String) key, value);
                    }
                    catch (Exception ex)
                    {
                        logger.warn("Failed copying one attribute");
                    }
                }
            }
        }
        catch (Exception ex)
        {
            logger.warn("Failed updateNonNullFields ",ex);
        }
    }
}
