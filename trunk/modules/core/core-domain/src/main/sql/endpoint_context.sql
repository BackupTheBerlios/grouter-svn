create table endpoint_context
(
    id bigint(20) not null auto_increment,
    idno varchar(36),
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    keyname varchar(1000) not null,
    value varchar(1000) not null,
    endpoint_fk varchar(36) not null,
    primary key (id),
    key(endpoint_fk),
    key(keyname),
    foreign key (endpoint_fk) references endpoint (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create index endpoint_context_name on endpoint_context (keyname);
