create table message
(
    id bigint(36) not null auto_increment,
 --   idno varchar(36) not null,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    content varchar(255) not null,
    node_fk bigint(20) not null,
    foreign key (node_fk ) references node (id),
    sender_fk varchar(36),
--    foreign key (sender_fk) references sender (id),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;