create table job_state
(
    id bigint(20) not null,
    idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into job_state (id, name) values (1,'NOTSTARTED');
insert into job_state (id, name) values (2,'RUNNING');
insert into job_state (id, name) values (3,'FINISHED');
insert into job_state (id, name) values (4,'STOPPED');
insert into job_state (id, name) values (5,'ERROR');


