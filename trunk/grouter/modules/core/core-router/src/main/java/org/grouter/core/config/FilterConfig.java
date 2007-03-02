package org.grouter.core.config;

/**
 * Created by IntelliJ IDEA.
 * User: geopol
 * Date: Sep 13, 2006
 * Time: 9:47:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilterConfig
{
    private String filter;


    /**
     * Constructor.
     * @param filter
     * @throws IllegalArgumentException if filter == null
     */
    public FilterConfig(String filter)
    {
        if(filter == null)
        {
            throw new IllegalArgumentException("Can not use a null filter as parameter");
        }
        this.filter = filter;
    }


    /**
     * Getter.
     * @return
     */
    public String getFilter()
    {
        return filter;
    }
}
