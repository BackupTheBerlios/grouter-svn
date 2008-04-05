create table job_event
(
    id bigint(20) not null auto_increment,
    idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    message varchar(1000) not null,
    created datetime not null,
    position bigint(20) not null,
    processtime_ms bigint(20) not null,
    job_fk bigint(20) not null,
    primary key (id),
    foreign key (job_fk) references job (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;