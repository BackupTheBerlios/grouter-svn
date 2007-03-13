package org.grouter.domain.entities;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Georges Polyzois
 */
public class EndPointType
{
    Long id;
    String name;
    public static EndPointType FILE_WRITER =  new EndPointType( 1L, "fileWriter" );
    public static EndPointType FILE_READER =  new EndPointType( 2L, "fileReader" );
    static Map<Long, EndPointType> values = new HashMap<Long,EndPointType>();

    static
    {
        values.put( FILE_WRITER.getId(), FILE_WRITER );
        values.put( FILE_READER.getId(), FILE_READER );
    }

    public static EndPointType valueOf( Long id )
    {
        return values.get( id );
    }


    public EndPointType(Long id, String name)
    {
        this.name = name;
        this.id = id;
    }


    public EndPointType()
    {
    }

    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
