package org.grouter.domain.entities;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;


public class Role implements Serializable, Comparable
{
    private Long id;
    private String name;
    private String displayName;
    public static final Role ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role REVIEWER = new Role(2L, "ROLE_REVIEWER");
    public static final Role SUPER_REVIEWER = new Role(3L, "ROLE_SUPER_REVIEWER");
    public final static Role EDITOR = new Role(4L, "ROLE_EDITOR");

    private final static Map<Long, Role> valueOfMap = new LinkedHashMap<Long, Role>(4);

    static
    {
        valueOfMap.put(ADMIN.getId(), ADMIN);
        valueOfMap.put(REVIEWER.getId(), REVIEWER);
        valueOfMap.put(SUPER_REVIEWER.getId(), SUPER_REVIEWER);
        valueOfMap.put(EDITOR.getId(), EDITOR);
    }


    Role()
    {
    }

    private Role(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }


    public static Role valueOf(Long id)
    {
        return valueOfMap.get(id);
    }

    public static List<Role> values()
    {
        return new ArrayList<Role>(valueOfMap.values());
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


    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }


    public String toString()
    {
        return ToStringBuilder.reflectionToString( this );
    }



    /**
     * Sorting inmemory using Collections.sort will do sort by id.
     *
     * @param anotherRole is a non-null Role.
     * @throws NullPointerException if anotherRole is null.
     * @throws ClassCastException   if anotherRole is not an Role object.
     */
    public int compareTo( Object anotherRole ) throws ClassCastException
    {
        //optimizing
        if ( this == anotherRole )
        {
            return 0;
        }

        Role compareToRole = ( Role ) anotherRole;
        return getId(  ).compareTo( compareToRole.getId() );
    }

}
