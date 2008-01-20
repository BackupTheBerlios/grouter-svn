/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.grouter.domain.entities;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain entity representing a scheduled job.  A Job can be in different states.
 * <p/>
 * A Job can be in a state - {@link JobState} -
 *
 * @author Georges Polyzois
 */
@Entity
@Table(name = "job")
//@Indexed( index="indexes/job" )  // Entity will be indexed for querying using Hibernate SystemServiceImpl
public class Job extends BaseEntity
{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @DocumentId
    Long id;

    @Column(name = "displayname")
    @NotNull
    @Field(index = Index.TOKENIZED, store = Store.YES)
    String displayName;

    @Column(name = "jobordernumber")
    @NotNull
    Long jobOrderNumber;

    @ManyToOne
    @NotNull
    JobGroup jobGroup;

    @Column(name = "cron_expression")
    @NotNull
    @Field(index = Index.TOKENIZED, store = Store.YES)
    String cronExpression;

    //List<JobEvent> jobEvents = new ArrayList<JobEvent>();

    @ManyToOne
    @JoinColumn(name = "job_state_fk")
    @NotNull
    JobState jobState;

    @Column
    @Field(index = Index.TOKENIZED, store = Store.YES)
    Date startedOn;

    @Column
    @Field(index = Index.TOKENIZED, store = Store.YES)
    Date finishedAt;


    @ManyToOne
    @JoinColumn(name = "job_type_fk")
    @NotNull
    JobType jobType;

    @ManyToOne
    @JoinColumn(name = "router_fk")
    @NotNull
    Router router;

    // on or off
//    Boolean enabled;

    @OneToMany(targetEntity = org.grouter.domain.entities.JobContext.class,
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Map jobContext = new HashMap();


    public Job() {
    }

    public Job(final Long id, final String displayName, final String cronExpression,
               final JobState jobState,
               final JobType jobType,
               final Router router
    ) {
        this.id = id;
        this.displayName = displayName;
        this.cronExpression = cronExpression;
        this.jobState = jobState;
        this.jobType = jobType;
        this.router = router;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(final String cronExpression) {
        this.cronExpression = cronExpression;
    }


    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(final JobState jobState) {
        this.jobState = jobState;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(final JobType jobType) {
        this.jobType = jobType;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(final Router router) {
        this.router = router;
    }

    public Map<String, String> getJobContext() {
        return jobContext;
    }

    public void setJobContext(final Map<String, String> jobContext) {
        this.jobContext = jobContext;
    }

    public Date getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(final Date startedOn) {
        this.startedOn = startedOn;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(final Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Long getJobOrderNumber() {
        return jobOrderNumber;
    }

    public void setJobOrderNumber(Long jobOrderNumber) {
        this.jobOrderNumber = jobOrderNumber;
    }
}
