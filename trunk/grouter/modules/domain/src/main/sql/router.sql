create table router
(
    ID varchar(255) not null,
    STARTEDON timestamp,
    UPTIME bigint,
    NAME varchar(255),
    primary key (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;