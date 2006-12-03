package org.grouter.domain.entities.systemuser;


public class Role
{
    private String roleName;
    private static final Role[] allRoles;

    public static final Role VIEW_DATA ;

    public static final Role UPDATE_DATA ;
    public static final Role SECURITY ;

    public static final Role SYSTEM ;

    static
    {
        VIEW_DATA = new Role("viewdate");
        UPDATE_DATA = new Role("updatedata");
        SECURITY = new Role("security");
        SYSTEM = new Role("system");
        allRoles = new Role[]
                {
                        Role.VIEW_DATA,
                        Role.UPDATE_DATA,
                        Role.SECURITY,
                        Role.SYSTEM
                };
    }

    private Role(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleName()
    {
        return roleName;
    }
   
}
