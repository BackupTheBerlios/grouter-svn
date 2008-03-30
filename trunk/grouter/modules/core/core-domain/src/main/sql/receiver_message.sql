create table receiver_message
(
    message_fk bigint(20) not null,
    receiver_fk varchar(36) not null,
    primary key (message_fk, receiver_fk),
    foreign key (message_fk) references message (id),
    foreign key (receiver_fk) references receiver (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;