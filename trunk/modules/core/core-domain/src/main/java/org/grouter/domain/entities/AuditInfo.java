package org.grouter.domain.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Embeddable;
import javax.persistence.Column;
import java.util.Date;

/**
 * All entities extends the Basentity which holds a reference to an AuditInfo instance.
 * Auditinfo is an embeddable composite used to store information about who and when an entity
 * was edited.
 *
 * @author Georges Polyzois
 */
@Embeddable
public class AuditInfo
{
    @Column(name = "createdon")
    //@Field(index = Index.TOKENIZED, store = Store.YES)
    Date createdOn;
    @Column(name = "modifiedon")
    //@Field(index = Index.TOKENIZED, store = Store.YES)
    Date modifiedOn;
    @ManyToOne
    @JoinColumn(name = "createdby")
    User createdBy;
    @ManyToOne
    @JoinColumn(name = "modifiedby")
    User modifiedBy;


    public Date getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn)
    {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn()
    {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn)
    {
        this.modifiedOn = modifiedOn;
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
