create table message
(
    id bigint(36) not null auto_increment,
 --   idno bigint(20) auto_increment,
    createdon datetime,
    modifiedon datetime,
    createdby bigint(20),
    modifiedby bigint(20),
    sender_fk varchar(36),
    content varchar(255) not null,
    node_fk varchar(34) not null,
    foreign key (node_fk ) references node (id)  ON DELETE CASCADE,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;