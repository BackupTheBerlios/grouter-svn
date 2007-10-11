create table node
(
    id varchar(36) not null,
    createdon timestamp,
    modifiedon timestamp,
    displayname varchar(255),
    description varchar(2048),
    statusmessage varchar(1024),
    createdirectories boolean,
    source varchar(36),
    receiver varchar(36),
    internalqueueurl varchar(1000),
    backupuri varchar(1000),
    inbound_endpoint_fk varchar(36),
    outbound_endpoint_fk varchar(36),
    router_fk varchar(255)  not null,
    nodestatus_fk bigint(20),
    primary key (id),
    foreign key (router_fk) references router (id),
    foreign key (inbound_endpoint_fk) references endpoint (id),
    foreign key (outbound_endpoint_fk) references endpoint (id),
    foreign key (nodestatus_fk) references nodestatus (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;