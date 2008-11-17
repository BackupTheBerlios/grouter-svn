create table router
(

    id varchar(36) not null,
    -- id bigint(20) not null,
    -- idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    startedon timestamp,
    uptime bigint,
    rmiregistryport int,
    rmiserviceport int,
    displayname varchar(255) not null ,
    description varchar(2000) not null,
    homepath varchar(2048) not null,
--    settings_fk varchar(36) not null,
    settings_fk bigint(20) not null,
    primary key (id),
    FOREIGN KEY (createdby) REFERENCES  user (id),
    FOREIGN KEY (modifiedby) REFERENCES  user (id),
    foreign key (settings_fk) references settings (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 