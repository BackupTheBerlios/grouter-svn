package org.grouter.domain.entities;

/**
 * A filter can be set om some EndPoints. A file filter or JMS selector filter could
 * be used.
 *
 * @author Georges Polyzois
 */
public class Filter
{
    FilterType filterType;

    public FilterType getFilterType()
    {
        return filterType;
    }

    public void setFilterType(FilterType filterType)
    {
        this.filterType = filterType;
    }
}
