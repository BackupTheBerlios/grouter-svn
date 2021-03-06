create table job_context
(
    id bigint(20) not null auto_increment,
    idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    keyname varchar(1000) not null,
    value varchar(1000) not null,
    job_fk varchar(36) not null,
    primary key (id),
    key(job_fk),
    key(keyname),
    foreign key (job_fk) references job (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



create index job_context_index on job_context (keyname);
