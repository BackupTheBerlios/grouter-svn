package org.grouter.domain.entities;

import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A Job is part of a JobGroup.  A JobGroup can hold one or more Jobs
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "jobgroup")
public class JobGroup {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;


    // An ordered list of jobs - execute one job at a time
    @OneToMany(mappedBy = "jobGroup")
    @OrderBy("jobOrderNumber")
    private List<Job> jobs = new ArrayList<Job>();

                  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
