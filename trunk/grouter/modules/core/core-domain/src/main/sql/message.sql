create table message
(
    id varchar(36) not null,
    creationtimestamp timestamp,
    content varchar(255) not null,
    counter bigint(20) ,
    node_fk varchar(36) not null,
    foreign key (node_fk ) references node (id),
    sender_fk varchar(36),
--    foreign key (sender_fk) references sender (id),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;