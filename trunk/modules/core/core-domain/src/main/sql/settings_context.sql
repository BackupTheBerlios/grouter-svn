create table settings_context
(
    id bigint(20) not null auto_increment,
    idno varchar(36),
    createdon timestamp,
    modifiedon timestamp,
    createdby bigint(20),
    modifiedby bigint(20),
    keyname varchar(1000) not null,
    value varchar(1000) not null,
    settings_fk bigint(20) NOT NULL,
    primary key (id),
    key(settings_fk),
    key(keyname),
    foreign key (settings_fk) references settings (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create index settings_context_name on settings_context (keyname);
