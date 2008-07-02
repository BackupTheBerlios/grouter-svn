create table job_state
(
    id bigint(20) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    name varchar(255),
    description varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into job_state (id,createdon, modifiedon,createdby,modifiedby, name) values (1,NOW(),NOW(),1,1,'PENDING', 'Starting status when job is not started yet');
insert into job_state (id, createdon, modifiedon,createdby,modifiedby,name) values (2,NOW(),NOW(),1,1,'RUNNING', 'Job is running');
insert into job_state (id,createdon, modifiedon,createdby,modifiedby, name) values (3,NOW(),NOW(),1,1,'STOPPING', 'Job is stopping');
insert into job_state (id,createdon, modifiedon,createdby,modifiedby, name) values (4,NOW(),NOW(),1,1,'STOPPED', 'Job has stopped');
insert into job_state (id,createdon, modifiedon,createdby,modifiedby, name) values (5,NOW(),NOW(),1,1,'ERROR', 'Job has error and can not continue processing');


