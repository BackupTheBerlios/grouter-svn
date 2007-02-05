create table message
(
    ID varchar(255) not null,
    CREATIONTIMESTAMP timestamp,
    MESSAGE varchar(255) not null,
    SENDER_FK varchar(255),
    NODE_FK varchar(255),
    primary key (ID),
    foreign key (NODE_FK) references node (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;