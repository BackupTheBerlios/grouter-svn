package org.grouter.domain.entities;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;


public class Role
{
    private Long id;
    private String name;
    public static final Role ADMIN = new Role(1L, "ROLE_ADMIN");
    public static final Role REVIEWER = new Role(2L, "ROLE_REVIEWER");
    public static final Role SUPER_REVIEWER = new Role(3L, "ROLE_SUPER_REVIEWER");
    public static final Role ADVERTISER = new Role(4L, "ROLE_ADVERTISER");
    public final static Role EDITOR = new Role(5L, "ROLE_EDITOR");

    private final static Map<Long, Role> valueOfMap = new LinkedHashMap<Long, Role>(5);

    static
    {
        valueOfMap.put(ADMIN.getId(), ADMIN);
        valueOfMap.put(REVIEWER.getId(), REVIEWER);
        valueOfMap.put(SUPER_REVIEWER.getId(), SUPER_REVIEWER);
        valueOfMap.put(ADVERTISER.getId(), ADVERTISER);
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


}
