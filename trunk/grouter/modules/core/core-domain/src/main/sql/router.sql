create table router
(
    id varchar(36) not null,
    startedon timestamp,
    uptime bigint,
    rmiregistryport int,
    rmiserviceport int,
    displayname varchar(255) not null ,
    description varchar(2000) not null,
    homepath varchar(2048) not null,
    settings_fk varchar(36) not null,
    primary key (id),
    foreign key (settings_fk) references settings (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;