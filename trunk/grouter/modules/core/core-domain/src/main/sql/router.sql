create table router
(
    id varchar(36) not null,
    startedon timestamp,
    uptime bigint,
    rmiregistryport int,
    rmiserviceport int,
    name varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;