create table endpoint_filereader
(
    id varchar(36) not null,
    createdon timestamp,
    modifiedon timestamp,
    filter_type_fk bigint(20),
    router_fk varchar(255),
    primary key (id),
    foreign key (router_fk) references router (id),
    foreign key (filter_type_fk) references filter_type (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;