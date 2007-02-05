create table receiver_message
(
    MESSAGE_FK varchar(255) not null,
    RECEIVER_FK varchar(255) not null,
    primary key (MESSAGE_FK, RECEIVER_FK),
    foreign key (MESSAGE_FK) references MESSAGE (ID),
    foreign key (RECEIVER_FK) references RECEIVER (ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;