create table endpoint
(
    id bigint(20) NOT NULL auto_increment,
    idno varchar(36) ,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    uri varchar(1000),
    clazzname varchar(1000),
    cron varchar(1000),
    endpoint_type_fk bigint(20) not null,
    filter_type_fk bigint(20),
    primary key (id),
    foreign key (endpoint_type_fk) references endpoint_type (id)
--    foreign key (filter_type_fk) references filter_type (id)   Do not  know why we can not use this constraint..
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

