package org.grouter.domain.entities;

import java.util.Date;

/**
 * All entities extends the Basentity which holds a reference to an AuditInfo instance.
 *
 * @author Georges Polyzois
 */
public class AuditInfo
{
    Date created;
    Date modified;
    User createdBy;
    User modifiedBy;


    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getModified()
    {
        return modified;
    }

    public void setModified(Date modified)
    {
        this.modified = modified;
    }

    public User getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(User createdBy)
    {
        this.createdBy = createdBy;
    }

    public User getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
}
