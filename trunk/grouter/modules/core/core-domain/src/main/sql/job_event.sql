create table job_event
(
    id bigint(20) not null auto_increment,
    message varchar(1000) not null,
    created datetime not null,
    job_fk bigint(20) not null,
    primary key (id),
    foreign key (job_fk) references job (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;