create table node
(
    id varchar(36) not null,
    createdon timestamp,
    modifiedon timestamp,
    displayname varchar(255),
    description varchar(2048),
    statusmessage varchar(1024),
    receiverstatic varchar(255),
    senderstatic varchar(255),
    internalqueueurl varchar(1000),
    backupuri varchar(1000),
    inbound_endpoint_fk varchar(36),
    outbound_endpoint_fk varchar(36),
    router_fk varchar(255),
    nodestatus_fk bigint(20),
    primary key (id),
    foreign key (router_fk) references router (id),
    foreign key (inbound_endpoint_fk) references endpoint (id),
    foreign key (outbound_endpoint_fk) references endpoint (id),
    foreign key (nodestatus_fk) references nodestatus (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;