create table node
(
    ID varchar(255) not null,
    CRETEDON timestamp,
    MODIFIEDON timestamp,
    MODIFIEDBYSYSTEMUSER varbinary(255),
    NAME varchar(255),
    ROUTER_FK varchar(255),
    primary key (ID),
    foreign key (ROUTER_FK) references router (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;