create table node
(
    -- id is given by unique name of the node
    id varchar(36) not null,
    idno varchar(36),
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    displayname varchar(255),
    description varchar(2048),
    statusmessage varchar(1024),
    createdirectories boolean,
    source varchar(36),
    receiver varchar(36),
    internalqueueurl varchar(2048),
    backupuri varchar(1000),
    inbound_endpoint_fk varchar(36),
    outbound_endpoint_fk varchar(36),
    router_fk varchar(36)  not null,
    nodestatus_fk bigint(20),
    primary key (id),
    foreign key (router_fk) references router (id),
    foreign key (inbound_endpoint_fk) references endpoint (id),
    foreign key (outbound_endpoint_fk) references endpoint (id),
    foreign KEY (createdby) REFERENCES  user (id),
    foreign KEY (modifiedby) REFERENCES  user (id),
    foreign key (nodestatus_fk) references nodestatus (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;