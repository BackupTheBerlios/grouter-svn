package org.grouter.domain.entities;

import org.hibernate.validator.NotNull;

import javax.persistence.*;

/**
 * Domain entity.
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity implements Comparable
{
    private Long id;
    private User user;
    private Role role;

    public UserRole()
    {
    }

    public UserRole(User user, Role role)
    {
        this.user = user;
        this.role = role;
    }


    @Id
    @Column(name = "id")
    @GeneratedValue( strategy=GenerationType.AUTO )
    @NotNull
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }


    public void setUser(User user)
    {
        this.user = user;
    }


    public Role getRole()
    {
        return role;
    }


    public void setRole(Role role)
    {
        this.role = role;
    }


    /**
     * Sorting inmemory using Collections.sort will do sort by name.
     *
     * @param anotherObject is a non-null Role.
     * @throws NullPointerException if anotherObject is null.
     * @throws ClassCastException   if anotherObject is not an Role object.
     */
    public int compareTo(Object anotherObject) throws ClassCastException
    {
        if (this == anotherObject)
        {
            return 0;
        }

        UserRole compareTo = (UserRole) anotherObject;
        return getId().compareTo(compareTo.getId());
    }

}
