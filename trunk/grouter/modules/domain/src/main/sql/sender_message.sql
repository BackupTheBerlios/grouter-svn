create table sender_message
(
    SENDER_FK varchar(255) not null,
    MESSAGE_FK varchar(255) not null,
    primary key (SENDER_FK, MESSAGE_FK),
    unique (MESSAGE_FK),
    foreign key (MESSAGE_FK) references message (ID),
    foreign key (SENDER_FK) references sender (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

