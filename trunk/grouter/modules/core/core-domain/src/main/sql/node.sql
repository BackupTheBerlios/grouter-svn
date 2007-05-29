create table node
(
    id varchar(36) not null,
    createdon timestamp,
    modifiedon timestamp,
    name varchar(255),
    receiverstatic varchar(255),
    senderstatic varchar(255),
    internalqueueurl varchar(1000),
    backupurl varchar(1000),
    inbound_endpoint_fk varchar(36),
    outbound_endpoint_fk varchar(36),
    router_fk varchar(255),
    primary key (id),
    foreign key (router_fk) references router (id),
    foreign key (inbound_endpoint_fk) references endpoint (id),
    foreign key (outbound_endpoint_fk) references endpoint (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;