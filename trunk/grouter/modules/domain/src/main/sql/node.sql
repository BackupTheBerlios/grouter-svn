create table node
(
    id varchar(36) not null,
    createdon timestamp,
    modifiedon timestamp,
    name varchar(255),
    router_fk varchar(255),
    primary key (id),
    foreign key (router_fk) references router (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;