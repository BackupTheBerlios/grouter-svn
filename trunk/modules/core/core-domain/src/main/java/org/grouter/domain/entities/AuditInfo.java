package org.grouter.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
    private Date createdOn;
    @Column(name = "modifiedon")
    //@Field(index = Index.TOKENIZED, store = Store.YES)
    private Date modifiedOn;
    //@ManyToOne
    //@JoinColumn(name = "createdby")
    @Column(name = "createdby")
    private Long createdBy;
    //@ManyToOne
    //@JoinColumn(name = "modifiedby")
    @Column(name = "modifiedby")
    private Long modifiedBy;

    public AuditInfo()
    {
        this.modifiedOn = new Date();
    }

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
